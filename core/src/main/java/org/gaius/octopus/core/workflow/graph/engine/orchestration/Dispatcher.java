package org.gaius.octopus.core.workflow.graph.engine.orchestration;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.core.workflow.graph.engine.event.EventHandler;
import org.gaius.octopus.core.workflow.graph.engine.event.EventManager;
import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 分发器
 *
 * @author gaius.zhao
 * @date 2025/9/30
 */
@Slf4j
public class Dispatcher {
    
    /**
     * 事件队列
     */
    private final ArrayBlockingQueue<GraphNodeEventBase> eventQueue;
    
    /**
     * 事件处理器
     */
    private final EventHandler eventHandler;
    
    /**
     * 事件管理器
     */
    private final EventManager eventManager;
    
    /**
     * 执行协调器
     */
    private final ExecutionCoordinator executionCoordinator;
    
    private volatile boolean running = false;
    
    private Thread dispatcherThread;
    
    public Dispatcher(ArrayBlockingQueue<GraphNodeEventBase> eventQueue, EventHandler eventHandler,
            ExecutionCoordinator executionCoordinator, EventManager eventManager) {
        this.eventQueue = eventQueue;
        this.eventHandler = eventHandler;
        this.eventManager = eventManager;
        this.executionCoordinator = executionCoordinator;
    }
    
    /**
     * 启动
     */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        dispatcherThread = Thread.ofVirtual().name("GraphDispatcher").start(this::dispatcherLoop);
    }
    
    /**
     * 停止
     */
    public void stop() {
        running = false;
        dispatcherThread.interrupt();
    }
    
    /**
     * 循环分发
     */
    private void dispatcherLoop() {
        try {
            while (running) {
                // 检查命令
                executionCoordinator.checkCommands();
                
                // 检查扩展
                executionCoordinator.checkScaling();
                
                // 处理事件
                try {
                    GraphNodeEventBase event = eventQueue.take();
                    eventHandler.dispatch(event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                
                // 检查执行是否完成
                if (executionCoordinator.isExecutionComplete()) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Dispatcher error", e);
            executionCoordinator.markFailed(e);
        } finally {
            executionCoordinator.markComplete();
            if (eventManager != null) {
                eventManager.markComplete();
            }
        }
    }
    
}
