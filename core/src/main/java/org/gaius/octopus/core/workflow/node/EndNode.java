package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.List;
import java.util.Map;

/**
 * 结束节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class EndNode extends AbstractNode {
    
    public EndNode(String id, Map<String, Object> data) {
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
