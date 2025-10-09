package org.gaius.octopus.core.workflow.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 节点状态
 *
 * @author gaius.zhao
 * @date 2025/10/9
 */
@Getter
public enum NodeStateEnum {
    /**
     * 未知
     */
    UNKNOWN("UNKNOWN"),
    /**
     * 节点已取
     */
    TAKEN("TAKEN"),
    
    /**
     * 节点已跳过
     */
    SKIPPED("SKIPPED"),
    ;
    
    NodeStateEnum(String value) {
        this.value = value;
    }
    
    private final String value;
    
    private static final Map<String, NodeStateEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (NodeStateEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.value, targetEnum);
        }
    }
    
    @Nullable
    public static NodeStateEnum resolve(@Nullable String value) {
        return (value != null ? MAPPINGS.get(value) : null);
    }
    
}
