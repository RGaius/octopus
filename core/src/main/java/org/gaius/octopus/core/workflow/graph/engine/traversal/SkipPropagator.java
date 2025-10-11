package org.gaius.octopus.core.workflow.graph.engine.traversal;

import org.gaius.octopus.core.workflow.graph.Edge;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.StateManager;
import org.gaius.octopus.core.workflow.graph.record.EdgeStateAnalysisRecord;

import java.util.List;
import java.util.Set;

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
        for (Edge edge : edges) {
            this.stateManager.markEdgeSkipped(edge.getId());
            this.propagateSkipFromEdge(edge.getId());
        }
    }
    
    /**
     * 跳过边
     * <p>
     * 从跳过的边缘递归地传播跳过状态
     * </p>
     * <p>
     * 规则:
     *     <ul>
     *         <li>
     *              如果节点具有任何未知的传入边，则停止处理
     *         </li>
     *         <li>
     *              如果节点的所有传入边被跳过，则跳过节点及其边
     *         </li>
     *         <li>
     *             如果节点的任意传入边被获取，则当前节点仍可被执行
     *         </li>
     *     </ul>
     * </p>
     *
     * @param edgeId 边ID
     */
    private void propagateSkipFromEdge(String edgeId) {
        Edge edge = this.graph.getEdges().get(edgeId);
        String downstreamNodeId = edge.getTarget();
        // 获取下游节点的所有传入边
        Set<Edge> incomingEdges = this.graph.getIncomingEdges(downstreamNodeId);
        // 分析边的状态
        EdgeStateAnalysisRecord edgeStateAnalysisRecord = this.stateManager.analyzeEdgeStates(incomingEdges);
        if (edgeStateAnalysisRecord.hasUnknown()) {
            return;
        }
        // 如果有已获取的边,则当前节点仍可被执行
        if (edgeStateAnalysisRecord.hasTaken()) {
            this.stateManager.enqueueNode(downstreamNodeId);
            return;
        }
        if (edgeStateAnalysisRecord.allSkipped()) {
            this.propagateSkipToNode(downstreamNodeId);
        }
    }
    
    /**
     * 跳过节点
     * <p>
     * 标记当前节点和它所有的出边为跳过状态
     * </p>
     *
     * @param nodeId 节点ID
     */
    private void propagateSkipToNode(String nodeId) {
        this.stateManager.markNodeSkipped(nodeId);
        Set<Edge> outgoingEdges = this.graph.getOutgoingEdges(nodeId);
        for (Edge edge : outgoingEdges) {
            this.stateManager.markEdgeSkipped(edge.getId());
            this.propagateSkipFromEdge(edge.getId());
        }
    }
}
