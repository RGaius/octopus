package org.gaius.octopus.core.workflow.graph.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.time.LocalDateTime;

/**
 * 节点运行异常
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NodeRunExceptionEvent extends GraphNodeEventBase {
    
    /**
     * 错误信息
     */
    private String error;
    
    /**
     * 运行开始时间
     */
    private LocalDateTime startAt;
    
    public NodeRunExceptionEvent(String id, String nodeId, NodeTypeEnum nodeType, LocalDateTime startAt,
            NodeRunResultRecord result, String error) {
        super(id, nodeId, nodeType, null, null, result);
        this.error = error;
        this.startAt = startAt;
    }
    
    @Override
    public String getEventName() {
        return "NodeRunExceptionEvent";
    }
}
