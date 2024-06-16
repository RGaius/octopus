package org.gaius.octopus.common.model;

import lombok.Data;

/**
 * @author zhaobo
 * @program octopus
 * @description 基础查询对象
 * @date 2024/6/16
 */
@Data
public class BaseQuery {
    
    /**
     * 每页条数
     */
    private Integer size = 10;
    
    /**
     * 当前页
     */
    private Integer current = 1;
}
