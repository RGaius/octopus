package org.gaius.octopus.core.workflow.node;

import lombok.Data;
import org.gaius.octopus.core.workflow.enums.ErrorStrategyEnum;
import org.gaius.octopus.core.workflow.enums.NodeExecutionTypeEnum;
import org.gaius.octopus.core.workflow.enums.NodeStateEnum;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.node.entity.RetryConfig;

import java.util.List;
import java.util.Map;

/**
 * 节点抽象类
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
@Data
public abstract class AbstractNode {
    
    /**
     * 节点ID
     */
    protected String id;
    
    /**
     * 节点名称
     */
    protected String name;
    
    /**
     * 节点状态
     */
    protected NodeStateEnum state;
    
    /**
     * 节点数据
     */
    protected Map<String, Object> data;
    
    /**
     * 节点执行类型
     */
    private NodeExecutionTypeEnum executionType;
    
    /**
     * 是否重试
     */
    private Boolean retry = false;
    
    public AbstractNode(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }
    
    /**
     * 初始化节点对象
     * <p>
     * 节点数据验证与准备
     * </p>
     */
    public abstract void init();
    
    /**
     * 节点运行
     *
     * @return 节点运行结果
     */
    public abstract List<GraphNodeEventBase> run();
    
    /**
     * 获取错误策略
     *
     * @return
     */
    public ErrorStrategyEnum errorStrategy() {
        String errorStrategy = getErrorStrategy();
        return ErrorStrategyEnum.resolve(errorStrategy);
    }
    
    /**
     * 获取异常策略
     *
     * @return
     */
    protected abstract String getErrorStrategy();
    
    /**
     * 获取重试配置
     *
     * @return
     */
    public RetryConfig getRetryConfig() {
        return new RetryConfig();
    }
}
