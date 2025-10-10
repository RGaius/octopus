package org.gaius.octopus.core.workflow.graph.engine.traversal;

import org.gaius.octopus.core.workflow.graph.Edge;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.StateManager;

import java.util.List;

/**
 * 跳过传播器
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class SkipPropagator {
    
    /**
     * 图
     */
    private final Graph graph;
    
    /**
     * 状态管理
     */
    private final StateManager stateManager;
    
    public SkipPropagator(Graph graph, StateManager stateManager) {
        this.graph = graph;
        this.stateManager = stateManager;
    }
    
    /**
     * 跳过分支路径
     *
     * @param edges 边集合
     */
    public void skipBranchPaths(List<Edge> edges) {
    
    }
}
