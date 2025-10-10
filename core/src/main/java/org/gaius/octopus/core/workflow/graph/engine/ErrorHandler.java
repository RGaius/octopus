package org.gaius.octopus.core.workflow.graph.engine;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.constant.WorkflowNodeExecutionMetadataConstant;
import org.gaius.octopus.core.workflow.enums.ErrorStrategyEnum;
import org.gaius.octopus.core.workflow.enums.WorkflowNodeExecutionStatusEnum;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.engine.domain.NodeExecution;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.graph.events.NodeRunExceptionEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunFailedEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunRetryEvent;
import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Slf4j
public class ErrorHandler {
    
    /**
     * 图
     */
    private final Graph graph;
    
    /**
     * 图执行
     */
    private final GraphExecution graphExecution;
    
    public ErrorHandler(Graph graph, GraphExecution graphExecution) {
        this.graph = graph;
        this.graphExecution = graphExecution;
    }
    
    public GraphNodeEventBase handleNodeFailure(NodeRunFailedEvent event) {
        AbstractNode node = graph.getNodes().get(event.getNodeId());
        NodeExecution nodeExecution = graphExecution.createNodeIfAbsent(event.getNodeId());
        int retryCount = nodeExecution.getRetryCount();
        
        // 首先检查是否配置了重试且未超过最大重试次数
        if (node.getRetry() && retryCount < node.getRetryConfig().getMaxRetries()) {
            GraphNodeEventBase result = handleRetry(event, retryCount);
            if (result != null) {
                // 重试计数将在处理 NodeRunRetryEvent 时增加
                return result;
            }
        }
        
        // 应用配置的错误策略
        ErrorStrategyEnum strategy = node.errorStrategy();
        
        return switch (strategy) {
            case FAIL_BRANCH -> handleFailBranch(event);
            case DEFAULT_VALUE -> handleDefaultValue(event);
            case null -> handleAbort(event);
        };
    }
    
    /**
     * 错误处理-失败分支
     *
     * @param event
     * @return
     */
    private GraphNodeEventBase handleAbort(NodeRunFailedEvent event) {
        log.error("Node {} failed with ABORT strategy: {}", event.getNodeId(), event.getError());
        // 返回 null 表示应该停止执行
        return null;
    }
    
    /**
     * 错误处理-重试
     *
     * @param event      节点运行失败事件
     * @param retryCount 重试次数
     * @return
     */
    private GraphNodeEventBase handleRetry(NodeRunFailedEvent event, int retryCount) {
        AbstractNode node = graph.getNodes().get(event.getNodeId());
        
        // 检查是否已超过最大重试次数
        if (!node.getRetry() || retryCount >= node.getRetryConfig().getMaxRetries()) {
            return null;
        }
        
        // 等待重试间隔
        try {
            Thread.sleep(node.getRetryConfig().getRetryInterval() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
        
        // 创建重试事件
        return new NodeRunRetryEvent(event.getId(), node.getName(), event.getNodeId(), event.getNodeType(),
                event.getNodeRunResult(), event.getStartAt(), event.getError(), retryCount + 1);
    }
    
    /**
     * 错误处理-失败分支
     *
     * @param event
     * @return
     */
    private GraphNodeEventBase handleFailBranch(NodeRunFailedEvent event) {
        Map<String, Object> outputs = new HashMap<>();
        outputs.put("error_message", event.getNodeRunResult().error());
        outputs.put("error_type", event.getNodeRunResult().errorType());
        
        NodeRunResultRecord result = new NodeRunResultRecord(WorkflowNodeExecutionStatusEnum.EXCEPTION,
                event.getNodeRunResult().inputs(), event.getNodeRunResult().processData(), outputs, "fail-branch",
                Map.of(WorkflowNodeExecutionMetadataConstant.ERROR_STRATEGY, ErrorStrategyEnum.FAIL_BRANCH));
        
        return new NodeRunExceptionEvent(event.getId(), event.getNodeId(), event.getNodeType(), event.getStartAt(),
                result, event.getError());
    }
    
    /**
     * 错误处理-默认值
     *
     * @param event
     * @return
     */
    private GraphNodeEventBase handleDefaultValue(NodeRunFailedEvent event) {
        Map<String, Object> outputs = new HashMap<>();
        outputs.put("error_message", event.getNodeRunResult().error());
        outputs.put("error_type", event.getNodeRunResult().errorType());
        
        NodeRunResultRecord result = new NodeRunResultRecord(WorkflowNodeExecutionStatusEnum.EXCEPTION,
                event.getNodeRunResult().inputs(), event.getNodeRunResult().processData(), outputs,
                Map.of(WorkflowNodeExecutionMetadataConstant.ERROR_STRATEGY, ErrorStrategyEnum.DEFAULT_VALUE));
        
        return new NodeRunExceptionEvent(event.getId(), event.getNodeId(), event.getNodeType(), event.getStartAt(),
                result, event.getError());
    }
    
    
}
