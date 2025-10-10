package org.gaius.octopus.core.workflow.graph.events;

/**
 * 节点运行成功
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class NodeRunSucceededEvent extends GraphNodeEventBase {
    
    @Override
    public String getEventName() {
        return "NodeRunSucceededEvent";
    }
}
