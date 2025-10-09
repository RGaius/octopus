package org.gaius.octopus.core.workflow.graph.engine.worker;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhaobo
 * @program octopus
 * @description 工作者
 * @date 2025/10/9
 */
@Slf4j
public class Worker implements Runnable {
    
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
     * 停止标记
     */
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);
    
    public Worker(ArrayBlockingQueue<String> readyQueue, ArrayBlockingQueue<GraphNodeEventBase> eventQueue, Graph graph) {
        this.readyQueue = readyQueue;
        this.eventQueue = eventQueue;
        this.graph = graph;
    }
    
    @Override
    public void run() {
        while (!stopFlag.get()) {
            try {
                // 获取节点ID
                String nodeId = readyQueue.take();
                // 获取节点
                AbstractNode node = graph.getNodes().get(nodeId);
                // 运行节点
                List<GraphNodeEventBase> eventBases = node.run();
                for (GraphNodeEventBase eventBase : eventBases) {
                    // 添加事件
                    eventQueue.put(eventBase);
                }
            } catch (InterruptedException e) {
                log.error("worker interrupted", e);
                stopFlag.set(true);
                Thread.currentThread().interrupt();
            }
        }
    }
}
