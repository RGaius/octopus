package org.gaius.octopus.core.workflow;

import lombok.Data;
import org.gaius.octopus.core.workflow.enums.SystemVariableKeyEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统变量
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Data
public class SystemVariable {
    
    // 用户ID
    private String userId;
    
    // 应用ID（可选）
    private String appId;
    
    // 工作流ID（可选）
    private String workflowId;
    
    // 文件列表
    private List<File> files = new ArrayList<>();
    
    // 工作流执行ID（序列化时使用别名 workflow_run_id）
    private String workflowExecutionId;
    
    // 查询内容
    private String query;
    
    // 对话ID
    private String conversationId;
    
    // 对话计数
    private Integer dialogueCount;
    
    public static SystemVariable empty() {
        return null;
    }
    
    public Map<SystemVariableKeyEnum, Object> toMap() {
        Map<SystemVariableKeyEnum, Object> map = new HashMap<>();
        
        map.put(SystemVariableKeyEnum.FILES, this.files);
        
        if (this.userId != null) {
            map.put(SystemVariableKeyEnum.USER_ID, this.userId);
        }
        
        if (this.appId != null) {
            map.put(SystemVariableKeyEnum.APP_ID, this.appId);
        }
        
        if (this.workflowId != null) {
            map.put(SystemVariableKeyEnum.WORKFLOW_ID, this.workflowId);
        }
        
        if (this.workflowExecutionId != null) {
            map.put(SystemVariableKeyEnum.WORKFLOW_EXECUTION_ID, this.workflowExecutionId);
        }
        
        if (this.query != null) {
            map.put(SystemVariableKeyEnum.QUERY, this.query);
        }
        
        if (this.conversationId != null) {
            map.put(SystemVariableKeyEnum.CONVERSATION_ID, this.conversationId);
        }
        
        if (this.dialogueCount != null) {
            map.put(SystemVariableKeyEnum.DIALOGUE_COUNT, this.dialogueCount);
        }
        
        return map;
    }
    
}
