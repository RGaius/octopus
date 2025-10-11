package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.List;
import java.util.Map;

/**
 * 条件节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class ConditionNode extends AbstractNode {
    
    public ConditionNode(String id, Map<String, Object> data) {
        super(id, data);
    }
    
    @Override
    public void init() {
    
    }
    
    @Override
    public List<GraphNodeEventBase> run() {
        return List.of();
    }
    
    @Override
    protected String getErrorStrategy() {
        return "";
    }
}
