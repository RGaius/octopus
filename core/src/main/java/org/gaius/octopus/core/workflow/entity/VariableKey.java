package org.gaius.octopus.core.workflow.entity;

import lombok.Data;

/**
 * 变量key
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Data
public class VariableKey {
    
    private String nodeId;
    
    private String key;
    
    public VariableKey(String nodeId, String key) {
        this.nodeId = nodeId;
        this.key = key;
    }
}
