package org.gaius.octopus.core.workflow.node.entity;

import lombok.Data;

/**
 * 重试配置
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@Data
public class RetryConfig {
    
    /**
     * 最大重试次数
     */
    private int maxRetries;
    
    /**
     * 重试间隔
     */
    private int retryInterval;
    
    /**
     * 是否重试
     */
    private boolean retry;
}
