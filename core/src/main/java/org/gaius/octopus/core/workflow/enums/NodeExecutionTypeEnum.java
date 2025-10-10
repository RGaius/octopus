package org.gaius.octopus.core.workflow.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 节点执行类型
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Getter
public enum NodeExecutionTypeEnum {
    /**
     * 可执行
     */
    EXECUTABLE("executable"),
    /**
     * 分支
     */
    BRANCH("branch"),
    /**
     * 容器
     */
    CONTAINER("container"),
    /**
     * 根
     */
    ROOT("root"),
    
    /**
     * 响应
     */
    RESPONSE("response"),
    ;
    
    NodeExecutionTypeEnum(String value) {
        this.value = value;
    }
    
    private final String value;
    
    private static final Map<String, NodeExecutionTypeEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (NodeExecutionTypeEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.value, targetEnum);
        }
    }
    
    @Nullable
    public static NodeExecutionTypeEnum resolve(@Nullable String value) {
        return (value != null ? MAPPINGS.get(value) : null);
    }
    
}
