package org.gaius.octopus.core.workflow.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统变量枚举
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public enum SystemVariableKeyEnum {
    /**
     * 用户ID
     */
    USER_ID("user_id"),
    /**
     * 应用ID
     */
    APP_ID("app_id"),
    /**
     * 工作流ID
     */
    WORKFLOW_ID("workflow_id"),
    /**
     * 文件列表
     */
    FILES("files"),
    /**
     * 工作流执行ID
     */
    WORKFLOW_EXECUTION_ID("workflow_execution_id"),
    /**
     * 查询内容
     */
    QUERY("query"),
    
    CONVERSATION_ID("conversation_id"),
    DIALOGUE_COUNT("dialogue_count");
    
    private final String value;
    
    SystemVariableKeyEnum(String value) {
        this.value = value;
    }
    
    private static final Map<String, SystemVariableKeyEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (SystemVariableKeyEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.value, targetEnum);
        }
    }
    
    @Nullable
    public static SystemVariableKeyEnum resolve(@Nullable String value) {
        return (value != null ? MAPPINGS.get(value) : null);
    }
    
}
