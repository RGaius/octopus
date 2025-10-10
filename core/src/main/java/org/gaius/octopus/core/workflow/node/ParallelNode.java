package org.gaius.octopus.core.workflow.node;

import java.util.Map;

/**
 * 并行节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class ParallelNode extends AbstractNode {
    
    public ParallelNode(String id, Map<String, Object> data) {
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
