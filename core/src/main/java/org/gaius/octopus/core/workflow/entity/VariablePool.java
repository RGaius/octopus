package org.gaius.octopus.core.workflow.entity;

import org.gaius.octopus.core.workflow.SystemVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 变量池
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class VariablePool {
    
    private final Map<String, Map<String, Object>> variableDictionary = new HashMap<>();
    
    private final Map<String, Object> userInputs = new HashMap<>();
    
    private final SystemVariable systemVariables = new SystemVariable();
    
    // 正则表达式用于匹配变量模板
    private static final Pattern VARIABLE_PATTERN = Pattern.compile(
            "\\{\\{#([a-zA-Z0-9_]{1,50}(?:\\.[a-zA-Z_][a-zA-Z0-9_]{0,29}){1,10})#\\}\\}");
    
    public void add(VariableKey variableKey, Object value) {
        variableDictionary.computeIfAbsent(variableKey.getNodeId(), k -> new HashMap<>())
                .put(variableKey.getKey(), value);
    }
}
