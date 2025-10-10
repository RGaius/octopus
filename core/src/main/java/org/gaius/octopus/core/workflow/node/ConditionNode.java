package org.gaius.octopus.core.workflow.node;

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
    protected String getErrorStrategy() {
        return "";
    }
}
