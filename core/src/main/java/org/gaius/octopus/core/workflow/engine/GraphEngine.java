package org.gaius.octopus.core.workflow.engine;

import org.gaius.octopus.core.workflow.engine.domain.GraphExecution;
import org.gaius.octopus.core.workflow.graph.Graph;

/**
 * 图引擎
 *
 * @author gaius.zhao
 * @date 2025/9/29
 */
public class GraphEngine {
    
    /**
     * 图执行对象
     */
    private GraphExecution graphExecution;
    
    /**
     * 图
     */
    private Graph graph;
    
    
    /**
     * 运行
     */
    public void run() {
        graphExecution.start();
        // 创建图开始运行事件
    }
}
