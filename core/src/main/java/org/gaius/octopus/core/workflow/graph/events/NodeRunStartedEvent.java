package org.gaius.octopus.core.workflow.graph.events;

import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.time.LocalDateTime;

/**
 * 节点开始事件
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class NodeRunStartedEvent extends GraphNodeEventBase {
    
    /**
     * 节点名称
     */
    private String name;
    
    /**
     * 运行开始时间
     */
    private LocalDateTime startAt;
    
    public NodeRunStartedEvent(String id, String nodeId, NodeTypeEnum nodeType, String inLoopId, String inIterationId,
            NodeRunResultRecord result, LocalDateTime startAt) {
        super(id, nodeId, nodeType, inLoopId, inIterationId, result);
        this.startAt = startAt;
    }
    
    public NodeRunStartedEvent(String id, String nodeId, NodeTypeEnum nodeType, String name, String inIterationId,
            LocalDateTime startAt) {
        super(id, nodeId, nodeType, null, inIterationId, null);
        this.name = name;
        this.startAt = startAt;
    }
    
    @Override
    public String getEventName() {
        return "NodeRunFailedEvent";
    }
}
