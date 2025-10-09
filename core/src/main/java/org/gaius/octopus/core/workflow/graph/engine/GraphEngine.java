package org.gaius.octopus.core.workflow.graph.engine;

import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.engine.event.EventManager;
import org.gaius.octopus.core.workflow.graph.engine.worker.WorkerPool;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

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
    private GraphExecution graphExecution;
    
    /**
     * 图
     */
    private Graph graph;
    
    /**
     * 运行队列
     */
    private ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 事件队列
     */
    private ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 状态管理
     */
    private StateManager stateManager;
    
    /**
     * 事件管理
     */
    private EventManager eventManager;
    
    /**
     * 工作池
     */
    private WorkerPool workerPool;
    
    /**
     * 初始化
     */
    public GraphEngine(String workflowId, Graph graph) {
        this.graphExecution = new GraphExecution(workflowId);
        this.graph = graph;
        this.readyQueue = new ArrayBlockingQueue<>(100);
        this.eventQueue = new ArrayBlockingQueue<>(100);
        // 状态管理
        this.stateManager = new StateManager(this.graph, this.readyQueue);
        // 事件管理
        this.eventManager = new EventManager();
        // 初始化工作池
        this.workerPool = new WorkerPool(this.readyQueue, this.eventQueue, this.graph, 10, 5);
    }
    
    
    /**
     * 运行
     */
    public void run() {
        graphExecution.start();
        // 创建图开始运行事件
        this.startExecution();
    }
    
    /**
     * 开始执行
     */
    public void startExecution() {
        this.workerPool.start();
    }
}
