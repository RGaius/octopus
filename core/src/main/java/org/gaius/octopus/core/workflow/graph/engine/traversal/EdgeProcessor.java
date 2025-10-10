package org.gaius.octopus.core.workflow.graph.engine.traversal;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaius.octopus.core.workflow.enums.NodeExecutionTypeEnum;
import org.gaius.octopus.core.workflow.graph.Edge;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.StateManager;
import org.gaius.octopus.core.workflow.graph.record.CategorizeBranchRecord;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 边处理器
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class EdgeProcessor {
    
    /**
     * 图
     */
    private final Graph graph;
    
    /**
     * 状态管理
     */
    private final StateManager stateManager;
    
    /**
     * 跳过传播器
     */
    private final SkipPropagator skipPropagator;
    
    public EdgeProcessor(Graph graph, StateManager stateManager, SkipPropagator skipPropagator) {
        this.graph = graph;
        this.stateManager = stateManager;
        this.skipPropagator = skipPropagator;
    }
    
    /**
     * 处理节点成功
     *
     * @param nodeId         节点ID
     * @param selectedHandle 选中的边
     * @return
     */
    public List<String> processNodeSuccess(String nodeId, String selectedHandle) {
        AbstractNode node = this.graph.getNodes().get(nodeId);
        if (node.getExecutionType() == NodeExecutionTypeEnum.BRANCH) {
            return processBranchNodeEdge(nodeId, selectedHandle);
        }
        return processNonBranchNodeEdge(nodeId);
    }
    
    /**
     * 处理无分支节点
     *
     * @param nodeId 节点ID
     * @return
     */
    private List<String> processNonBranchNodeEdge(String nodeId) {
        List<String> readyNodes = new ArrayList<>();
        Set<Edge> outgoingEdges = this.graph.getOutgoingEdges(nodeId);
        for (Edge edge : outgoingEdges) {
            List<String> nodes = this.processTakenEdge(edge);
            if (CollectionUtils.isNotEmpty(nodes)) {
                readyNodes.addAll(nodes);
            }
        }
        return readyNodes;
    }
    
    /**
     * 处理分支节点
     *
     * @param nodeId         节点ID
     * @param selectedHandle 选中的边
     * @return
     */
    private List<String> processBranchNodeEdge(String nodeId, String selectedHandle) {
        if (StringUtils.isEmpty(selectedHandle)) {
            throw new IllegalArgumentException("selectedHandle is empty");
        }
        CategorizeBranchRecord categorizeBranchRecord = this.stateManager.categorizeBranchEdges(nodeId, selectedHandle);
        List<Edge> unselectedEdges = categorizeBranchRecord.unselectedEdges();
        unselectedEdges.forEach(this::processSkippedEdge);
        
        List<Edge> selectedEdges = categorizeBranchRecord.selectedEdges();
        List<String> readyNodes = new ArrayList<>();
        for (Edge selectedEdge : selectedEdges) {
            List<String> nodes = processTakenEdge(selectedEdge);
            if (CollectionUtils.isNotEmpty(nodes)) {
                readyNodes.addAll(nodes);
            }
        }
        return readyNodes;
    }
    
    /**
     * 处理已获取边
     *
     * @param edge 已获取的边
     * @return
     */
    private List<String> processTakenEdge(Edge edge) {
        this.stateManager.markEdgeTaken(edge.getId());
        List<String> readyNodes = new ArrayList<>();
        if (this.stateManager.isNodeReady(edge.getTarget())) {
            readyNodes.add(edge.getTarget());
        }
        return readyNodes;
    }
    
    /**
     * 处理跳过边
     *
     * @param edge 跳过的边
     */
    private void processSkippedEdge(Edge edge) {
        this.stateManager.markEdgeSkipped(edge.getId());
    }
    
    /**
     * 处理分支完成
     *
     * @param nodeId           节点ID
     * @param edgeSourceHandle 后续节点
     * @return
     */
    public List<String> handleBranchCompletion(String nodeId, String edgeSourceHandle) {
        if (StringUtils.isEmpty(edgeSourceHandle)) {
            throw new IllegalArgumentException("edgeSourceHandle is empty");
        }
        CategorizeBranchRecord categorizeBranchRecord = this.stateManager.categorizeBranchEdges(nodeId,
                edgeSourceHandle);
        this.skipPropagator.skipBranchPaths(categorizeBranchRecord.unselectedEdges());
        this.processNodeSuccess(nodeId, edgeSourceHandle);
        return null;
    }
}
