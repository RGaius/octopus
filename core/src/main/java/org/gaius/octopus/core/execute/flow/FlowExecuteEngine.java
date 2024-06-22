package org.gaius.octopus.core.execute.flow;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;

/**
 * @author zhaobo
 * @program octopus
 * @description 流程执行引擎
 * @date 2024/6/16
 */
public class FlowExecuteEngine {
    
    private static DefaultDirectedGraph<FlowNode, DefaultEdge> processGraph = new DefaultDirectedGraph<>(
            DefaultEdge.class);
    
    public static void test() {
        FlowNode google = new TestNode();
        google.setCode("google");
        FlowNode wikipedia = new TestNode();
        wikipedia.setCode("wikipedia");
        FlowNode jgrapht = new TestNode();
        jgrapht.setCode("jgrapht");
        
        // add the vertices
        processGraph.addVertex(google);
        processGraph.addVertex(wikipedia);
        processGraph.addVertex(jgrapht);
        
        // add edges to create linking structure
        processGraph.addEdge(jgrapht, wikipedia);
        processGraph.addEdge(google, jgrapht);
        processGraph.addEdge(google, wikipedia);
        
        // 获取开始节点
        FlowNode startNode = processGraph.vertexSet().stream().filter(node -> processGraph.inDegreeOf(node) == 0)
                .findFirst().get();
        System.out.println("start node:" + startNode.getCode());
        
        // 获取结束节点
        FlowNode endNode = processGraph.vertexSet().stream().filter(node -> processGraph.outDegreeOf(node) == 0)
                .findFirst().get();
        System.out.println("end node:" + endNode.getCode());
        
        // 获取某个节点的子节点
        Set<DefaultEdge> defaultEdges = processGraph.outgoingEdgesOf(startNode);
        defaultEdges.forEach(edge -> {
            FlowNode flowNode = processGraph.getEdgeTarget(edge);
            System.out.println(flowNode.getCode());
        });
    }
    
    public static void main(String[] args) {
        test();
    }
}
