package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.util.Map;

/**
 * 接口节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class InterfaceNode extends AbstractNode {
    
    public InterfaceNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
        super(nodeConfig, graphRuntimeState);
        this.nodeType = NodeTypeEnum.INTERFACE;
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
