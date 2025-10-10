package org.gaius.octopus.core.workflow.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 图运行状态
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Data
public class GraphRuntimeState {
    
    /**
     * 变量池
     */
    private final VariablePool variablePool;
    
    private final Map<String, Object> outputs;
    
    public GraphRuntimeState(VariablePool variablePool) {
        this.variablePool = variablePool;
        this.outputs = new HashMap<>();
    }
    
    /**
     * 设置输出
     *
     * @param key   输出key
     * @param value 输出值
     */
    public void setOutput(String key, Object value) {
        outputs.put(key, value);
    }
}
