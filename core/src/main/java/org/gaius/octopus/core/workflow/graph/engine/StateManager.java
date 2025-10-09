package org.gaius.octopus.core.workflow.graph.engine;

import org.gaius.octopus.core.workflow.graph.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zhaobo
 * @program octopus
 * @description 状态管理
 * @date 2025/10/9
 */
public class StateManager {
    
    /**
     * 图
     */
    private Graph graph;
    
    /**
     * 运行队列
     */
    private ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 执行中的节点
     */
    private Set<String> executingNodes;
    
    public StateManager(Graph graph, ArrayBlockingQueue<String> readyQueue) {
        this.graph = graph;
        this.readyQueue = readyQueue;
        this.executingNodes = new HashSet<>();
    }
}
