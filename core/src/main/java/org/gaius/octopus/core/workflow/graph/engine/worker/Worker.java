package org.gaius.octopus.core.workflow.graph.engine.worker;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zhaobo
 * @program octopus
 * @description 工作者
 * @date 2025/10/9
 */
@Slf4j
public class Worker implements Runnable {
    
    /**
     * 节点ID
     */
    private final String nodeId;
    
    /**
     * 事件队列
     */
    private final ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 图对象
     */
    private final Graph graph;
    
    public Worker(String nodeId, ArrayBlockingQueue<GraphNodeEventBase> eventQueue, Graph graph) {
        this.nodeId = nodeId;
        this.eventQueue = eventQueue;
        this.graph = graph;
    }
    
    @Override
    public void run() {
        try {
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
            Thread.currentThread().interrupt();
        }
    }
}
