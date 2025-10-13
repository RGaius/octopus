package org.gaius.octopus.core.workflow.graph.engine.event;

import org.gaius.octopus.core.workflow.graph.events.GraphNodeEventBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 事件管理
 *
 * @author zhaobo
 * @date 2025/10/9
 */
public class EventManager {
    
    private final List<GraphNodeEventBase> events;
    
    // 读写锁
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public EventManager() {
        events = new ArrayList<>();
    }
    
    public void markComplete() {
    }
    
    public void collect(GraphNodeEventBase event) {
        // 尝试获取写锁
        lock.writeLock().lock();
        try {
            // 添加事件
            events.add(event);
        } finally {
            // 释放写锁
            lock.writeLock().unlock();
        }
    }
    
}
