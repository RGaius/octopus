package org.gaius.octopus.core.workflow.node.enums;

import lombok.Getter;
import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.node.AbstractNode;
import org.gaius.octopus.core.workflow.node.CodeNode;
import org.gaius.octopus.core.workflow.node.ConditionNode;
import org.gaius.octopus.core.workflow.node.EndNode;
import org.gaius.octopus.core.workflow.node.InterfaceNode;
import org.gaius.octopus.core.workflow.node.ParallelNode;
import org.gaius.octopus.core.workflow.node.StartNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 节点类型
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
@Getter
public enum NodeTypeEnum {
    /**
     * 开始节点
     */
    START("start", StartNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new StartNode(nodeConfig, graphRuntimeState);
        }
    },
    
    /**
     * 结束节点
     */
    END("end", EndNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new EndNode(nodeConfig, graphRuntimeState);
        }
    },
    /**
     * 条件节点
     */
    CONDITION("condition", ConditionNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new ConditionNode(nodeConfig, graphRuntimeState);
        }
    },
    /**
     * 代码节点
     */
    CODE("code", CodeNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new CodeNode(nodeConfig, graphRuntimeState);
        }
    },
    /**
     * 接口节点
     */
    INTERFACE("interface", InterfaceNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new InterfaceNode(nodeConfig, graphRuntimeState);
        }
    },
    /**
     * 并行节点
     */
    PARALLEL("parallel", ParallelNode.class) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return new ParallelNode(nodeConfig, graphRuntimeState);
        }
    },
    /**
     * 分组节点
     */
    GROUP("group", null) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return null;
        }
    },
    /**
     * 循环节点
     */
    LOOP("loop", null) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return null;
        }
    },
    /**
     * jsonpath节点
     */
    JSONPATH("jsonpath", null) {
        @Override
        public AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState) {
            return null;
        }
    };
    
    NodeTypeEnum(String type, Class<? extends AbstractNode> nodeClass) {
        this.type = type;
        this.nodeClass = nodeClass;
    }
    
    /**
     * 节点类型
     */
    private final String type;
    
    /**
     * 节点类
     */
    private final Class<? extends AbstractNode> nodeClass;
    
    /**
     * 创建节点对象
     *
     * @param nodeConfig        节点配置
     * @param graphRuntimeState 图运行状态
     * @return
     */
    public abstract AbstractNode createNode(Map<String, Object> nodeConfig, GraphRuntimeState graphRuntimeState);
    
    /**
     * 节点类型映射
     */
    private static final Map<String, NodeTypeEnum> MAPPINGS = new HashMap<>(16);
    
    static {
        for (NodeTypeEnum targetEnum : values()) {
            MAPPINGS.put(targetEnum.type, targetEnum);
        }
    }
    
    /**
     * 根据类型获取枚举
     *
     * @param type 类型
     * @return
     */
    public static NodeTypeEnum resolve(String type) {
        return MAPPINGS.get(type);
    }
    
}
