package org.gaius.octopus.core.workflow.graph.engine.event;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.entity.VariableKey;
import org.gaius.octopus.core.workflow.entity.VariablePool;
import org.gaius.octopus.core.workflow.enums.NodeExecutionTypeEnum;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.ErrorHandler;
import org.gaius.octopus.core.workflow.graph.engine.StateManager;
import org.gaius.octopus.core.workflow.graph.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.engine.traversal.EdgeProcessor;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.graph.events.NodeRunExceptionEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunFailedEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunRetryEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunStartedEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunSucceededEvent;

import java.util.List;

/**
 * @author zhaobo
 * @program octopus
 * @description 事件处理
 * @date 2025/10/9
 */
@Slf4j
public class EventHandler {
    
    /**
     * 图对象
     */
    private final Graph graph;
    
    /**
     * 运行时状态
     */
    private final GraphRuntimeState graphRuntimeState;
    
    /**
     * 图执行器
     */
    private final GraphExecution graphExecution;
    
    /**
     * 事件管理
     */
    private final EventManager eventManager;
    
    /**
     * 状态管理
     */
    private final StateManager stateManager;
    
    /**
     * 边处理器
     */
    private final EdgeProcessor edgeProcessor;
    
    /**
     * 错误处理
     */
    private final ErrorHandler errorHandler;
    
    public EventHandler(Graph graph, GraphRuntimeState graphRuntimeState, GraphExecution graphExecution,
            EventManager eventManager, EdgeProcessor edgeProcessor, StateManager stateManager,
            ErrorHandler errorHandler) {
        this.graph = graph;
        this.graphRuntimeState = graphRuntimeState;
        this.graphExecution = graphExecution;
        this.eventManager = eventManager;
        this.stateManager = stateManager;
        this.edgeProcessor = edgeProcessor;
        this.errorHandler = errorHandler;
    }
    
    /**
     * 分发事件
     *
     * @param event 事件
     */
    public void dispatch(GraphNodeEventBase event) {
        if (event.getInLoopId() != null || event.getInIterationId() != null) {
            eventManager.collect(event);
            return;
        }
        dispatchEvent(event);
    }
    
    /**
     * 分发事件
     *
     * @param event 事件
     */
    private void dispatchEvent(GraphNodeEventBase event) {
        String eventName = event.getEventName();
        switch (eventName) {
            case "NodeRunStartedEvent" -> handleNodeStarted((NodeRunStartedEvent) event);
            case "NodeRunSucceededEvent" -> handleNodeSuccess((NodeRunSucceededEvent) event);
            case "NodeRunFailedEvent" -> handleNodeFailure((NodeRunFailedEvent) event);
            case "NodeRunExceptionEvent" -> handleNodeException((NodeRunExceptionEvent) event);
            case "NodeRunRetryEvent" -> handleNodeRetry((NodeRunRetryEvent) event);
            default -> {
                eventManager.collect(event);
                log.warn("Unhandled event type:{} ", event);
            }
        }
    }
    
    /**
     * 节点开始
     *
     * @param event 节点开始事件
     */
    private void handleNodeStarted(NodeRunStartedEvent event) {
        var nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        nodeExecution.markStarted(event.getId());
        eventManager.collect(event);
    }
    
    /**
     * 节点成功
     *
     * @param event 节点成功事件
     */
    private void handleNodeSuccess(NodeRunSucceededEvent event) {
        var nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        nodeExecution.markTaken();
        
        storeNodeOutputs(event);
        
        var node = graph.getNodes().get(event.getNodeId());
        List<String> readyNodes;
        
        if (node.getExecutionType() == NodeExecutionTypeEnum.BRANCH) {
            readyNodes = edgeProcessor.handleBranchCompletion(event.getNodeId(),
                    event.getNodeRunResult().edgeSourceHandle());
        } else {
            readyNodes = edgeProcessor.processNodeSuccess(event.getNodeId(), null);
        }
        
        readyNodes.forEach(nodeId -> {
            stateManager.enqueueNode(nodeId);
            stateManager.startExecution(nodeId);
        });
        
        stateManager.finishExecution(event.getNodeId());
        
        if (node.getExecutionType() == NodeExecutionTypeEnum.RESPONSE) {
            updateResponseOutputs(event);
        }
        
        eventManager.collect(event);
    }
    
    /**
     * 节点失败
     *
     * @param event 节点失败事件
     */
    private void handleNodeFailure(NodeRunFailedEvent event) {
        var nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        nodeExecution.markFailed(event.getError());
        
        var result = errorHandler.handleNodeFailure(event);
        
        if (result != null) {
            dispatch(result);
        } else {
            graphExecution.fail(event.getError());
            eventManager.collect(event);
            stateManager.finishExecution(event.getNodeId());
        }
    }
    
    /**
     * 存储节点输出
     *
     * @param event 节点成功事件
     */
    private void storeNodeOutputs(NodeRunSucceededEvent event) {
        VariablePool variablePool = graphRuntimeState.getVariablePool();
        event.getNodeRunResult().outputs()
                .forEach((key, value) -> variablePool.add(new VariableKey(event.getNodeId(), key), value));
    }
    
    /**
     * 更新响应输出
     *
     * @param event
     */
    private void updateResponseOutputs(NodeRunSucceededEvent event) {
        event.getNodeRunResult().outputs().forEach(graphRuntimeState::setOutput);
    }
    
    /**
     * 节点异常
     *
     * @param event 节点异常事件
     */
    private void handleNodeException(NodeRunExceptionEvent event) {
        var nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        nodeExecution.markTaken();
    }
    
    /**
     * 节点重试
     *
     * @param event 节点重试事件
     */
    private void handleNodeRetry(NodeRunRetryEvent event) {
        var nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        nodeExecution.incrementRetry();
    }
    
}
