package org.gaius.octopus.core.workflow;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.entity.VariablePool;
import org.gaius.octopus.core.workflow.graph.Graph;

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
    
    private Graph graph;
    
    public void run() {
        VariablePool variablePool = new VariablePool();
        GraphRuntimeState graphRuntimeState = new GraphRuntimeState(variablePool);
        this.graph = Graph.init(this.graphConfig, graphRuntimeState, this.workflowId);
    }
}
