package org.gaius.octopus.core.workflow.graph.engine.worker;

import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaobo
 * @program octopus
 * @description 工作池
 * @date 2025/10/9
 */
public class WorkerPool {
    
    /**
     * 准备队列
     */
    private ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 事件队列
     */
    private ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 图对象
     */
    private Graph graph;
    
    /**
     * 最大工作
     */
    private int maxWorkers;
    
    /**
     * 最小
     */
    private int minWorkers;
    
    /**
     * 执行器
     */
    private ExecutorService executor;
    
    public WorkerPool(ArrayBlockingQueue<String> readyQueue, ArrayBlockingQueue<GraphNodeEventBase> eventQueue, Graph graph,
            int maxWorkers, int minWorkers) {
        this.readyQueue = readyQueue;
        this.eventQueue = eventQueue;
        this.graph = graph;
        this.maxWorkers = maxWorkers;
        this.minWorkers = minWorkers;
        // 构建虚拟线程池
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }
    
    public void start() {
        // 基于节点数量生成虚拟线程
        Map<String, AbstractNode> nodes = graph.getNodes();
        int nodeCount = nodes.size();
        int initWorkers;
        // 若节点数量小于10
        if (nodeCount < 10) {
            initWorkers = minWorkers;
        } else if (nodeCount < 50) {
            initWorkers = Math.min(minWorkers + 1, maxWorkers);
        } else {
            initWorkers = Math.min(minWorkers + 2, maxWorkers);
        }
        for (int i = 0; i < initWorkers; i++) {
            executor.submit(new Worker(readyQueue, eventQueue, graph));
        }
    }
}
