package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.List;
import java.util.Map;

/**
 * 代码节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class CodeNode extends AbstractNode {
    
    public CodeNode(String id, Map<String, Object> data) {
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
