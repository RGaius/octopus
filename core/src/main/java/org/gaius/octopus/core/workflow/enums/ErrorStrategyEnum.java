package org.gaius.octopus.core.workflow.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常策略
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Getter
public enum ErrorStrategyEnum {
    /**
     * 错误处理-失败分支
     */
    FAIL_BRANCH("fail-branch"),
    /**
     * 错误处理-默认值
     */
    DEFAULT_VALUE("default-value");
    
    ErrorStrategyEnum(String value) {
        this.value = value;
    }
    
    private final String value;
    
    private static final Map<String, ErrorStrategyEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (ErrorStrategyEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.value, targetEnum);
        }
    }
    
    @Nullable
    public static ErrorStrategyEnum resolve(@Nullable String value) {
        return (value != null ? MAPPINGS.get(value) : null);
    }
    
}
