package org.gaius.octopus.core.workflow.engine.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 图执行
 *
 * @author gaius.zhao
 * @date 2025/10/9
 */
@Slf4j
public class GraphExecution {
    
    /**
     * 工作流ID
     */
    private String workflowId;
    
    /**
     * 是否已启动
     */
    private Boolean started = false;
    
    /**
     * 是否已完成
     */
    private Boolean completed = false;
    
    /**
     * 是否终止
     */
    private Boolean aborted = false;
    
    /**
     * 错误信息
     */
    private String errorMsg;
    
    /**
     * 节点执行详情
     */
    private Map<String, NodeExecution> nodeExecutions;
    
    /**
     * 开始
     */
    public void start() {
        if (this.started) {
            return;
        }
        this.started = true;
    }
    
    /**
     * 完成
     */
    public void complete() {
        if (!this.started) {
            //  todo 工作流未开始
            log.warn("工作流未开始");
            return;
        }
        if (this.completed) {
            // todo 已经完成
            return;
        }
        this.completed = true;
    }
    
    /**
     * 中断
     */
    public void abort(String errorMsg) {
        if (!this.started) {
            //  todo 工作流未开始
            log.warn("工作流未开始");
            return;
        }
        if (this.aborted) {
            // todo 已经中断
            return;
        }
        this.aborted = true;
        this.errorMsg = errorMsg;
    }
    
    /**
     * 失败
     */
    public void fail(String errorMsg) {
        if (!this.started) {
            //  todo 工作流未开始
            log.warn("工作流未开始");
            return;
        }
        if (this.aborted) {
            // todo 已经中断
            return;
        }
        this.completed = true;
        this.errorMsg = errorMsg;
    }
    
    /**
     * 获取运行节点，如果不存在则创建
     */
    public NodeExecution createNodeIfAbsent(String nodeId) {
        return this.nodeExecutions.computeIfAbsent(nodeId, k -> new NodeExecution(nodeId));
    }
    
    /**
     * 是否运行中
     */
    public boolean isRunning() {
        return this.started && !this.completed && !this.aborted;
    }
    
    /**
     * 是否存在异常
     */
    public boolean hasError() {
        return this.errorMsg != null;
    }
    
}
