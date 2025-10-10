package org.gaius.octopus.core.workflow.graph.engine;

import org.apache.commons.collections4.CollectionUtils;
import org.gaius.octopus.core.workflow.enums.NodeStateEnum;
import org.gaius.octopus.core.workflow.graph.Edge;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.record.CategorizeBranchRecord;
import org.gaius.octopus.core.workflow.graph.record.EdgeStateAnalysisRecord;
import org.gaius.octopus.core.workflow.node.AbstractNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

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
    private final Graph graph;
    
    /**
     * 运行队列
     */
    private final ArrayBlockingQueue<String> readyQueue;
    
    /**
     * 执行中的节点
     */
    private final Set<String> executingNodes;
    
    public StateManager(Graph graph, ArrayBlockingQueue<String> readyQueue) {
        this.graph = graph;
        this.readyQueue = readyQueue;
        this.executingNodes = new HashSet<>();
    }
    
    /**
     * 节点添加队列
     *
     * @param nodeId 节点ID
     */
    public void enqueueNode(String nodeId) {
        readyQueue.add(nodeId);
        AbstractNode abstractNode = graph.getNodes().get(nodeId);
        abstractNode.setState(NodeStateEnum.TAKEN);
    }
    
    /**
     * 标记节点跳过
     *
     * @param nodeId 节点ID
     */
    public void markNodeSkipped(String nodeId) {
        AbstractNode abstractNode = graph.getNodes().get(nodeId);
        abstractNode.setState(NodeStateEnum.SKIPPED);
    }
    
    /**
     * 节点是否就绪
     *
     * @param nodeId 节点ID
     */
    public boolean isNodeReady(String nodeId) {
        Set<Edge> incomingEdges = graph.getIncomingEdges(nodeId);
        // 如果没有依赖节点，则直接返回true
        if (CollectionUtils.isEmpty(incomingEdges)) {
            return true;
        }
        // 若任何一个边的状态为未知，则节点未准备就绪
        boolean anyMatch = incomingEdges.stream().anyMatch(edge -> edge.getState() == NodeStateEnum.UNKNOWN);
        if (anyMatch) {
            return false;
        }
        // 节点状态至少存在一个为TAKEN时，节点准备就绪
        return incomingEdges.stream().allMatch(edge -> edge.getState() == NodeStateEnum.TAKEN);
    }
    
    /**
     * 获取节点状态
     *
     * @param nodeId 节点ID
     */
    public NodeStateEnum getNodeState(String nodeId) {
        return graph.getNodes().get(nodeId).getState();
    }
    
    /**
     * 标记边获取
     *
     * @param edgeId 边ID
     */
    public void markEdgeTaken(String edgeId) {
        graph.getEdges().get(edgeId).setState(NodeStateEnum.TAKEN);
    }
    
    /**
     * 标记边跳过
     *
     * @param edgeId 边ID
     */
    public void markEdgeSkipped(String edgeId) {
        graph.getEdges().get(edgeId).setState(NodeStateEnum.SKIPPED);
    }
    
    /**
     * 分析边状态
     * <p>
     * 这一批边中是否存在未知状态、是否存在已被获取状态、是否全部跳过
     * </p>
     *
     * @param edges 边集合
     */
    public EdgeStateAnalysisRecord analyzeEdgeStates(Set<Edge> edges) {
        Set<NodeStateEnum> states = edges.stream().map(Edge::getState).collect(Collectors.toSet());
        return new EdgeStateAnalysisRecord(states.contains(NodeStateEnum.UNKNOWN), states.contains(NodeStateEnum.TAKEN),
                states.contains(NodeStateEnum.SKIPPED));
    }
    
    /**
     * 获取边状态
     */
    public NodeStateEnum getEdgeState(String edgeId) {
        return graph.getEdges().get(edgeId).getState();
    }
    
    /**
     * 将分支边缘分类为选定和未选定
     *
     * @param nodeId         节点ID
     * @param selectedEdgeId 选定边缘ID
     */
    public CategorizeBranchRecord categorizeBranchEdges(String nodeId, String selectedEdgeId) {
        Set<Edge> outgoingEdges = graph.getOutgoingEdges(nodeId);
        List<Edge> selectedEdges = new ArrayList<>(outgoingEdges.size());
        List<Edge> unselectedEdges = new ArrayList<>(outgoingEdges.size());
        for (Edge edge : outgoingEdges) {
            if (edge.getId().equals(selectedEdgeId)) {
                selectedEdges.add(edge);
            } else {
                unselectedEdges.add(edge);
            }
        }
        return new CategorizeBranchRecord(selectedEdges, unselectedEdges);
    }
    
    /**
     * 开始执行
     *
     * @param nodeId 节点ID
     */
    public void startExecution(String nodeId) {
        executingNodes.add(nodeId);
    }
    
    /**
     * 完成执行
     *
     * @param nodeId 节点ID
     */
    public void finishExecution(String nodeId) {
        executingNodes.remove(nodeId);
    }
    
    /**
     * 是否在执行中
     *
     * @param nodeId 节点ID
     */
    public boolean isExecuting(String nodeId) {
        return executingNodes.contains(nodeId);
    }
    
    /**
     * 获取执行中的总数
     */
    public int getExecutingCount() {
        return executingNodes.size();
    }
    
    /**
     * 获取执行中节点
     */
    public Set<String> getExecutingNodes() {
        return new HashSet<>(executingNodes);
    }
    
    /**
     * 清除执行中节点
     */
    public void clearExecuting() {
        executingNodes.clear();
    }
    
    /**
     * 是否执行完成
     */
    public boolean isExecutionComplete() {
        return readyQueue.isEmpty() && executingNodes.isEmpty();
    }
}
