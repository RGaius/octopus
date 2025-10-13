package org.gaius.octopus.core.workflow.graph.engine.worker;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工作池
 * <p>
 * 持续监听准备就绪队列，并提交任务到执行器中
 * </p>
 *
 * @author zhaobo
 * @date 2025/10/9
 */
@Slf4j
public class WorkerPool {
    
    /**
     * 准备队列
     */
    private final ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 事件队列
     */
    private final ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 图对象
     */
    private final Graph graph;
    
    /**
     * 执行器
     */
    private final ExecutorService executor;
    
    /**
     * 队列监听执行器
     */
    private final ExecutorService queueListenerExecutor;
    
    public WorkerPool(ArrayBlockingQueue<String> readyQueue, ArrayBlockingQueue<GraphNodeEventBase> eventQueue,
            Graph graph) {
        this.readyQueue = readyQueue;
        this.eventQueue = eventQueue;
        this.graph = graph;
        // 构建虚拟线程池
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.queueListenerExecutor = Executors.newSingleThreadExecutor();
    }
    
    /**
     * 启动
     * <p>
     * 从准备就绪队列中获取节点ID，并提交任务到执行器中
     * </p>
     */
    public void start() {
        // 使用单线程监听准备就绪队列
        queueListenerExecutor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String nodeId = readyQueue.take();
                    executor.submit(new Worker(nodeId, eventQueue, graph));
                }
            } catch (InterruptedException e) {
                log.error("worker pool interrupted", e);
                Thread.currentThread().interrupt();
            }
        });
    }
}
