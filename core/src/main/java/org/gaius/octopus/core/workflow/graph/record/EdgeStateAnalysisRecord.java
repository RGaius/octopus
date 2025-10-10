package org.gaius.octopus.core.workflow.graph.record;

/**
 * 边状态分析结果
 *
 * @param hasUnknown 是否存在未知状态
 * @param hasTaken   存在已被获取状态
 * @param hasSkipped 存在已被跳过状态
 */
public record EdgeStateAnalysisRecord(Boolean hasUnknown, Boolean hasTaken, Boolean hasSkipped) {

}
