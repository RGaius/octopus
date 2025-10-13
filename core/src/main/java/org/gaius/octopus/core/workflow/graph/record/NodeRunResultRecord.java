package org.gaius.octopus.core.workflow.graph.record;

import lombok.Builder;
import org.gaius.octopus.core.workflow.enums.WorkflowNodeExecutionStatusEnum;

import java.util.Map;

/**
 * 节点运行结果记录
 *
 * @param inputs     输入
 * @param outputs    输出
 * @param error      错误
 * @param errorType  错误类型
 * @param retryIndex 重试索引
 */
@Builder
public record NodeRunResultRecord(WorkflowNodeExecutionStatusEnum statusEnum, Map<String, Object> inputs,
                                  Map<String, Object> processData, Map<String, Object> outputs,
                                  Map<String, Object> metadata, String edgeSourceHandle, String error, String errorType,
                                  int retryIndex) {
    
    public NodeRunResultRecord(WorkflowNodeExecutionStatusEnum workflowNodeExecutionStatusEnum,
            Map<String, Object> inputs, Map<String, Object> processData, Map<String, Object> outputs,
            Map<String, Object> errorStrategy) {
        this(workflowNodeExecutionStatusEnum, inputs, processData, outputs, errorStrategy, null, null, null, 0);
    }
    
    public NodeRunResultRecord(WorkflowNodeExecutionStatusEnum workflowNodeExecutionStatusEnum,
            Map<String, Object> inputs, Map<String, Object> processData, Map<String, Object> outputs,
            String edgeSourceHandle, Map<String, Object> errorStrategy) {
        this(workflowNodeExecutionStatusEnum, inputs, processData, outputs, errorStrategy, edgeSourceHandle, null, null,
                0);
    }
}

