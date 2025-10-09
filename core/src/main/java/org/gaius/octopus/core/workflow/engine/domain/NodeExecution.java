package org.gaius.octopus.core.workflow.engine.domain;

import lombok.Data;
import org.gaius.octopus.core.workflow.enums.NodeStateEnum;

/**
 * 节点执行
 *
 * @author gaius.zhao
 * @date 2025/10/9
 */
@Data
public class NodeExecution {
    
    /**
     * 节点ID
     */
    private String nodeId;
    
    /**
     * 节点状态
     */
    private NodeStateEnum state = NodeStateEnum.UNKNOWN;
    
    /**
     * 重试次数
     */
    private int retryCount = 0;
    
    /**
     * 执行ID
     */
    private String executionId;
    
    /**
     * 错误信息
     */
    private String errorMsg;
    
    public NodeExecution() {
    }
    
    public NodeExecution(String nodeId) {
        this.nodeId = nodeId;
    }
    
    /**
     * 标记开始
     *
     * @param executionId 执行ID
     */
    public void markStart(String executionId) {
        this.state = NodeStateEnum.TAKEN;
        this.executionId = executionId;
    }
    
    /**
     * 标记获取
     */
    public void markTaken() {
        this.state = NodeStateEnum.TAKEN;
        this.errorMsg = null;
    }
    
    /**
     * 标记失败
     */
    public void markFailed(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    /**
     * 标记跳过
     */
    public void markSkipped() {
        this.state = NodeStateEnum.SKIPPED;
    }
    
    /**
     * 增加重试
     */
    public void addRetry() {
        this.retryCount++;
    }
}
