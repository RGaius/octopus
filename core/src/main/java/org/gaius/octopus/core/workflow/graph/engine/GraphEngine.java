package org.gaius.octopus.core.workflow.graph.engine;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.engine.event.EventHandler;
import org.gaius.octopus.core.workflow.graph.engine.event.EventManager;
import org.gaius.octopus.core.workflow.graph.engine.orchestration.EventDispatcher;
import org.gaius.octopus.core.workflow.graph.engine.orchestration.ExecutionCoordinator;
import org.gaius.octopus.core.workflow.graph.engine.traversal.EdgeProcessor;
import org.gaius.octopus.core.workflow.graph.engine.traversal.SkipPropagator;
import org.gaius.octopus.core.workflow.graph.engine.worker.WorkerPool;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 图引擎
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class GraphEngine {
    
    /**
     * 图执行对象
     */
    private final GraphExecution graphExecution;
    
    /**
     * 图
     */
    private final Graph graph;
    
    /**
     * 图运行状态
     */
    private final GraphRuntimeState graphRuntimeState;
    
    /**
     * 运行队列
     */
    private final ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 事件队列
     */
    private final ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 状态管理
     */
    private final StateManager stateManager;
    
    /**
     * 事件管理
     */
    private final EventManager eventManager;
    
    /**
     * 错误处理
     */
    private final ErrorHandler errorHandler;
    
    /**
     * 跳过传播器
     */
    private final SkipPropagator skipPropagator;
    
    /**
     * 边处理器
     */
    private final EdgeProcessor edgeProcessor;
    
    /**
     * 事件处理器
     */
    private final EventHandler eventHandler;
    
    /**
     * 工作池
     */
    private final WorkerPool workerPool;
    
    /**
     * 执行协调器
     */
    private final ExecutionCoordinator executionCoordinator;
    
    /**
     * 分发器
     */
    private final EventDispatcher eventDispatcher;
    
    /**
     * 初始化
     */
    public GraphEngine(String workflowId, Graph graph, GraphRuntimeState graphRuntimeState) {
        this.graphExecution = new GraphExecution(workflowId);
        this.graph = graph;
        this.graphRuntimeState = graphRuntimeState;
        this.readyQueue = new ArrayBlockingQueue<>(100);
        this.eventQueue = new ArrayBlockingQueue<>(100);
        // 状态管理
        this.stateManager = new StateManager(this.graph, this.readyQueue);
        // 事件管理
        this.eventManager = new EventManager();
        
        // 异常处理
        this.errorHandler = new ErrorHandler(this.graph, this.graphExecution);
        
        this.skipPropagator = new SkipPropagator(this.graph, this.stateManager);
        this.edgeProcessor = new EdgeProcessor(this.graph, this.stateManager, this.skipPropagator);
        this.eventHandler = new EventHandler(this.graph, this.graphRuntimeState, this.graphExecution, this.eventManager,
                this.edgeProcessor, this.stateManager, this.errorHandler);
        // 初始化工作池
        this.workerPool = new WorkerPool(this.readyQueue, this.eventQueue, this.graph);
        this.executionCoordinator = new ExecutionCoordinator(this.graphExecution, this.stateManager, this.eventHandler,
                eventManager, this.workerPool);
        this.eventDispatcher = new EventDispatcher(this.eventQueue, this.eventHandler, this.executionCoordinator,
                this.eventManager);
    }
    
    
    /**
     * 运行
     */
    public void run() {
        // 开始执行
        this.graphExecution.start();
        // 创建图开始运行事件
        this.startExecution();
    }
    
    /**
     * 开始执行
     */
    public void startExecution() {
        // 开始工作池
        this.workerPool.start();
        // 获取开始节点
        AbstractNode startNode = this.graph.getStartNode();
        String nodeId = startNode.getId();
        // 添加到运行队列
        this.stateManager.enqueueNode(nodeId);
        // 启动执行
        this.stateManager.startExecution(nodeId);
        // 启动分发器
        this.eventDispatcher.start();
    }
}
