package org.gaius.octopus.core.workflow.graph;

import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.gaius.octopus.core.workflow.node.AbstractNode;
import org.gaius.octopus.core.workflow.node.NodeFactory;
import org.gaius.octopus.core.workflow.node.StartNode;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 图对象
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class Graph {
    
    /**
     * 节点
     */
    @Getter
    private Map<String, AbstractNode> nodes;
    
    /**
     * 图
     */
    @Getter
    private DefaultDirectedGraph<String, Edge> directedGraph;
    
    /**
     * 开始节点ID
     */
    @Getter
    private String startNodeId;
    
    public Graph() {
        this.nodes = new HashMap<>();
        this.directedGraph = new DefaultDirectedGraph<>(null, Edge::new, false);
    }
    
    
    /**
     * 初始化图对象
     * <p>
     * 基于有向图原理生成 Graph 对象
     * </p>
     *
     * @return
     */
    public static Graph init(Map<String, Object> graphConfig) {
        // 节点列表
        List<Map<String, Object>> nodes = (List<Map<String, Object>>) MapUtils.getObject(graphConfig, "nodes");
        
        // 边列表
        List<Map<String, Object>> edges = (List<Map<String, Object>>) MapUtils.getObject(graphConfig, "edges");
        
        // 创建图对象
        Graph graph = new Graph();
        DefaultDirectedGraph<String, Edge> directedGraph = graph.directedGraph;
        nodes.forEach(nodeConfig -> {
            String nodeId = MapUtils.getString(nodeConfig, "id");
            directedGraph.addVertex(nodeId);
            AbstractNode nodeInstance = NodeFactory.createNode(nodeConfig);
            if (nodeInstance instanceof StartNode) {
                graph.startNodeId = nodeId;
            }
            graph.nodes.put(nodeId, nodeInstance);
        });
        
        AtomicInteger edgeId = new AtomicInteger(0);
        edges.forEach(edgeConfig -> {
            String source = MapUtils.getString(edgeConfig, "source");
            String target = MapUtils.getString(edgeConfig, "target");
            Edge edge = directedGraph.addEdge(source, target);
            edge.setId("edge_%d".formatted(edgeId.getAndIncrement()));
        });
        return graph;
    }
    
    
    /**
     * 获取目标节点子节点列表
     *
     * @param targetNodeId 目标节点ID
     */
    public List<String> getOutgoingEdges(String targetNodeId) {
        return directedGraph.outgoingEdgesOf(targetNodeId).stream().map(directedGraph::getEdgeTarget).toList();
    }
    
    /**
     * 获取源节点父节点列表
     *
     * @param sourceNodeId 源节点ID
     */
    public List<String> getIncomingEdges(String sourceNodeId) {
        return directedGraph.incomingEdgesOf(sourceNodeId).stream().map(directedGraph::getEdgeSource).toList();
    }
    
}
