package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.util.Map;

/**
 * 开始节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class StartNode extends AbstractNode {
    
    public StartNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
        super(nodeConfig, graphRuntimeState);
        this.nodeType = NodeTypeEnum.START;
    }
    
    @Override
    public void init() {
    
    }
    
    @Override
    public GraphNodeEventBase execution() {
        return null;
    }
    
    @Override
    protected String getErrorStrategy() {
        return "";
    }
}
