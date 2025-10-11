package org.gaius.octopus.core.workflow.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gaius.octopus.core.workflow.enums.NodeStateEnum;
import org.jgrapht.graph.DefaultEdge;

import java.io.Serial;

/**
 * 边对象
 *
 * @author gaius.zhao
 * @date 2025/10/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Edge extends DefaultEdge {
    
    @Serial
    private static final long serialVersionUID = -7026291970278919413L;
    
    /**
     * 边ID
     */
    private String id;
    
    /**
     * 源节点ID
     */
    private String source;
    
    /**
     * 目标节点ID
     */
    private String target;
    
    /**
     * 状态
     * <p>
     * 默认未知状态
     * </p>
     */
    private NodeStateEnum state = NodeStateEnum.UNKNOWN;
    
    public Edge() {
    }
    
    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
    }
}
