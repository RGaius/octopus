package org.gaius.octopus.core.workflow.graph.events;

import lombok.Data;
import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

/**
 * 节点事件
 *
 * @author gaius.zhao
 * @date 2025/9/30
 */
@Data
public abstract class GraphNodeEventBase {
    
    /**
     * 事件ID
     */
    private String id;
    
    /**
     * 节点ID
     */
    private String nodeId;
    
    /**
     * 节点类型
     */
    private NodeTypeEnum nodeType;
    
    /**
     * 循环id
     */
    private String inLoopId;
    
    /**
     * 迭代id
     */
    private String inIterationId;
    
    /**
     * 运行结果
     */
    private NodeRunResultRecord nodeRunResult;
    
    public GraphNodeEventBase() {
    }
    
    public GraphNodeEventBase(String id, String nodeId, NodeTypeEnum nodeType, String inLoopId, String inIterationId,
            NodeRunResultRecord result) {
        this.id = id;
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.inLoopId = inLoopId;
        this.inIterationId = inIterationId;
        this.nodeRunResult = result;
    }
    
    public abstract String getEventName();
}
