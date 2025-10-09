package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.List;
import java.util.Map;

/**
 * 节点抽象类
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public abstract class AbstractNode {
    
    /**
     * 节点ID
     */
    protected String id;
    
    /**
     * 节点数据
     */
    protected Map<String, Object> data;
    
    public AbstractNode(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }
    
    /**
     * 初始化节点对象
     * <p>
     * 节点数据验证与准备
     * </p>
     */
    public abstract void init();
    
    public List<GraphNodeEventBase> run() {
        return null;
    }
}
