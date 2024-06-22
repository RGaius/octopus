package org.gaius.octopus.core.execute.flow;

import java.util.Map;

/**
 * @author zhaobo
 * @program octopus
 * @description 流程节点
 * @date 2024/6/16
 */
public interface FlowNode {
    
    /**
     * 获取节点编号
     */
    String getCode();
    
    /**
     * 获取节点类型
     */
    String getType();
    
    /**
     * 获取节点内容
     */
    Map<String, Object> getContent();
    
    void setCode(String google);
}
