package org.gaius.octopus.core.workflow.graph.record;

import org.gaius.octopus.core.workflow.graph.Edge;

import java.util.List;

/**
 * 分支分类结果
 *
 * @param selectedEdges   选中的边
 * @param unselectedEdges 未选中的边
 */
public record CategorizeBranchRecord(List<Edge> selectedEdges, List<Edge> unselectedEdges) {

}
