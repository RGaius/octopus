package org.gaius.octopus.core.workflow.node;

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
    protected String getErrorStrategy() {
        return "";
    }
}
