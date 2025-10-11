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
    
    private LocalDateTime startAt;
    
    public NodeRunStartedEvent(String id, String nodeId, NodeTypeEnum nodeType, String inLoopId, String inIterationId,
            NodeRunResultRecord result, LocalDateTime startAt) {
        super(id, nodeId, nodeType, inLoopId, inIterationId, result);
        this.startAt = startAt;
    }
    
    @Override
    public String getEventName() {
        return "NodeRunFailedEvent";
    }
}
