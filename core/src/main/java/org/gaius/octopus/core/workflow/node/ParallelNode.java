package org.gaius.octopus.core.workflow.node;

import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.util.Map;

/**
 * 并行节点
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class ParallelNode extends AbstractNode {
    
    public ParallelNode(Map<String, Object> config, GraphRuntimeState graphRuntimeState) {
        super(config, graphRuntimeState);
        this.nodeType = NodeTypeEnum.PARALLEL;
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
