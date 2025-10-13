package org.gaius.octopus.core.workflow;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.graph.Graph;
import org.gaius.octopus.core.workflow.graph.engine.GraphEngine;

import java.util.Map;

/**
 * 工作流入口
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class WorkflowEntry {
    
    private String tenantId;
    
    private String appId;
    
    private String workflowId;
    
    private Map<String, Object> graphConfig;
    
    private GraphRuntimeState graphRuntimeState;
    
    private Graph graph;
    
    private GraphEngine graphEngine;
    
    public WorkflowEntry(String tenantId, String appId, String workflowId, Map<String, Object> graphConfig, Graph graph,
            GraphRuntimeState graphRuntimeState) {
        this.tenantId = tenantId;
        this.appId = appId;
        this.workflowId = workflowId;
        this.graphConfig = graphConfig;
        this.graph = graph;
        this.graphEngine = new GraphEngine(this.workflowId, this.graph, graphRuntimeState);
    }
    
    /**
     * 运行
     */
    public void run() {
        this.graphEngine.run();
    }
}
