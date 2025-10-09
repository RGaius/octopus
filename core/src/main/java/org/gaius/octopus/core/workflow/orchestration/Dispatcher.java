package org.gaius.octopus.core.workflow.orchestration;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

/**
 * 分发器
 *
 * @author gaius.zhao
 * @date 2025/9/30
 */
@Slf4j
public class Dispatcher {
    
    public void dispatch(GraphNodeEventBase event) {
        // 默认处理逻辑
        log.warn("Unhandled event type: {}", event.getClass().getSimpleName());
    }
}
