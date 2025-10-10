package org.gaius.octopus.core.workflow.graph.engine.orchestration;

import org.gaius.octopus.core.workflow.graph.engine.StateManager;
import org.gaius.octopus.core.workflow.graph.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.engine.event.EventHandler;
import org.gaius.octopus.core.workflow.graph.engine.event.EventManager;
import org.gaius.octopus.core.workflow.graph.engine.worker.WorkerPool;

/**
 * 执行协调器
 *
 * @author gaius.zhao
 * @date 2025/10/10
 */
public class ExecutionCoordinator {
    
    /**
     * 图执行对象
     */
    private final GraphExecution graphExecution;
    
    /**
     * 状态管理
     */
    private final StateManager stateManager;
    
    /**
     * 事件管理
     */
    private final EventManager eventManager;
    
    /**
     * 工作池
     */
    private final WorkerPool workerPool;
    
    public ExecutionCoordinator(GraphExecution graphExecution, StateManager stateManager, EventHandler eventHandler,
            EventManager eventManager, WorkerPool workerPool) {
        this.graphExecution = graphExecution;
        this.stateManager = stateManager;
        this.eventManager = eventManager;
        this.workerPool = workerPool;
    }
    
    public void checkCommands() {
    
    }
    
    public void checkScaling() {
    
    }
    
    /**
     * 检查执行是否完成
     *
     * @return
     */
    public boolean isExecutionComplete() {
        if (this.graphExecution.getStarted() || this.graphExecution.hasError()) {
            return true;
        }
        return this.stateManager.isExecutionComplete();
    }
    
    /**
     * 标记执行失败
     *
     * @param e 异常
     */
    public void markFailed(Exception e) {
        this.graphExecution.fail(e.getMessage());
    }
    
    /**
     * 标记执行完成
     */
    public void markComplete() {
        if (!this.graphExecution.getCompleted()) {
            this.graphExecution.complete();
        }
    }
}
