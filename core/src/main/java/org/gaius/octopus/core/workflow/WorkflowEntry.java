package org.gaius.octopus.core.workflow;

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
}
