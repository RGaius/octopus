package org.gaius.octopus.core.workflow.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 节点运作状态枚举
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Getter
public enum WorkflowNodeExecutionStatusEnum {
    /**
     * 等待中
     */
    PENDING("pending"),
    /**
     * 运行中
     */
    RUNNING("running"),
    /**
     * 成功
     */
    SUCCEEDED("succeeded"),
    /**
     * 失败
     */
    FAILED("failed"),
    /**
     * 异常
     */
    EXCEPTION("exception"),
    /**
     * 停止
     */
    STOPPED("stopped"),
    ;
    
    WorkflowNodeExecutionStatusEnum(String value) {
        this.value = value;
    }
    
    private final String value;
    
    private static final Map<String, WorkflowNodeExecutionStatusEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (WorkflowNodeExecutionStatusEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.value, targetEnum);
        }
    }
    
    @Nullable
    public static WorkflowNodeExecutionStatusEnum resolve(@Nullable String value) {
        return (value != null ? MAPPINGS.get(value) : null);
    }
    
}
