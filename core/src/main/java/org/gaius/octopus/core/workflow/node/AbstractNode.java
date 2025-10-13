package org.gaius.octopus.core.workflow.node;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaius.octopus.core.workflow.entity.GraphRuntimeState;
import org.gaius.octopus.core.workflow.enums.ErrorStrategyEnum;
import org.gaius.octopus.core.workflow.enums.NodeExecutionTypeEnum;
import org.gaius.octopus.core.workflow.enums.NodeStateEnum;
import org.gaius.octopus.core.workflow.enums.WorkflowNodeExecutionStatusEnum;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;
import org.gaius.octopus.core.workflow.graph.events.NodeRunFailedEvent;
import org.gaius.octopus.core.workflow.graph.events.NodeRunStartedEvent;
import org.gaius.octopus.core.workflow.graph.record.NodeRunResultRecord;
import org.gaius.octopus.core.workflow.node.entity.RetryConfig;
import org.gaius.octopus.core.workflow.node.enums.NodeTypeEnum;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 节点抽象类
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
@Data
@Slf4j
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
     * 节点配置
     */
    protected Map<String, Object> config;
    
    /**
     * 节点数据
     */
    protected Map<String, Object> data;
    
    /**
     * 节点执行类型
     */
    protected NodeTypeEnum nodeType;
    
    /**
     * 节点执行类型
     */
    private NodeExecutionTypeEnum executionType;
    
    /**
     * 执行ID
     */
    private String executeId;
    
    /**
     * 是否重试
     */
    private Boolean retry = false;
    
    /**
     * 节点开始时间
     */
    private LocalDateTime startAt;
    
    private GraphRuntimeState graphRuntimeState;
    
    public AbstractNode(Map<String, Object> config, GraphRuntimeState graphRuntimeState) {
        this.config = config;
        this.id = MapUtils.getString(config, "id");
        this.data = (Map<String, Object>) MapUtils.getMap(config, "data");
        this.graphRuntimeState = graphRuntimeState;
    }
    
    /**
     * 初始化节点对象
     * <p>
     * 节点数据验证与准备
     * </p>
     */
    public abstract void init();
    
    /**
     * 节点开始运行
     *
     * @param eventConsumer 事件消费者
     */
    public void run(Consumer<GraphNodeEventBase> eventConsumer) {
        if (StringUtils.isEmpty(this.executeId)) {
            // 使用工具生成uuid
            this.executeId = RandomStringUtils.randomAlphanumeric(32);
        }
        this.startAt = LocalDateTime.now();
        NodeRunStartedEvent startedEvent = new NodeRunStartedEvent(this.executeId, this.id, this.nodeType, this.name,
                null, this.startAt);
        eventConsumer.accept(startedEvent);
        
        try {
            GraphNodeEventBase event = this.execution();
            eventConsumer.accept(event);
        } catch (Exception e) {
            log.error("node {} run error", this.id, e);
            NodeRunResultRecord resultRecord = NodeRunResultRecord.builder()
                    .statusEnum(WorkflowNodeExecutionStatusEnum.FAILED).error(e.getMessage())
                    .errorType("WorkflowNodeError").build();
            NodeRunFailedEvent failedEvent = new NodeRunFailedEvent(this.executeId, this.id, this.nodeType,
                    this.startAt, resultRecord, e.getMessage());
            eventConsumer.accept(failedEvent);
        }
    }
    
    /**
     * 节点运行
     *
     * @return 节点运行结果
     */
    public abstract GraphNodeEventBase execution();
    
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
