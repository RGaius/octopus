package org.gaius.octopus.core.workflow.graph.events;

import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.time.LocalDateTime;

/**
 * 节点运行重试事件
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class NodeRunRetryEvent extends NodeRunStartedEvent {
    
    private String error;
    
    private Integer retryIndex;
    
    public NodeRunRetryEvent(String id, String name, String nodeId, NodeTypeEnum nodeType,
            NodeRunResultRecord nodeRunResult, LocalDateTime startAt, String error, int retryIndex) {
        super(id, name, nodeType, null, null, nodeRunResult, startAt);
        this.error = error;
        this.retryIndex = retryIndex;
    }
    
    @Override
    public String getEventName() {
        return "NodeRunFailedEvent";
    }
}
