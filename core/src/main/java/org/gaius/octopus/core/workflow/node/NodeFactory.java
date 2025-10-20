package org.gaius.octopus.core.workflow.node;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.util.Map;

/**
 * 节点工厂
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class NodeFactory {
    
    private GraphRuntimeState graphRuntimeState;
    
    public NodeFactory(GraphRuntimeState graphRuntimeState) {
        this.graphRuntimeState = graphRuntimeState;
    }
    
    /**
     * 创建节点对象
     */
    public AbstractNode createNode(Map<String, Object> nodeConfig) {
        // 获取节点ID
        String nodeId = MapUtils.getString(nodeConfig, "id");
        if (StringUtils.isEmpty(nodeId)) {
            throw new IllegalArgumentException("节点ID不能为空");
        }
        // 获取节点类型
        String nodeType = MapUtils.getString(nodeConfig, "type");
        if (StringUtils.isEmpty(nodeType)) {
            throw new IllegalArgumentException("节点类型不能为空");
        }
        // 判断当前节点类型是否正确
        NodeTypeEnum nodeTypeEnum = NodeTypeEnum.resolve(nodeType);
        if (nodeTypeEnum == null) {
            throw new IllegalArgumentException("节点类型错误");
        }
        // 获取节点数据
        Map<String, Object> nodeData = (Map<String, Object>) MapUtils.getMap(nodeConfig, "data");
        if (MapUtils.isEmpty(nodeData)) {
            throw new IllegalArgumentException("节点数据不能为空");
        }
        AbstractNode node = nodeTypeEnum.createNode(nodeConfig, this.graphRuntimeState);
        // 执行节点初始化
        node.init();
        return node;
    }
}
