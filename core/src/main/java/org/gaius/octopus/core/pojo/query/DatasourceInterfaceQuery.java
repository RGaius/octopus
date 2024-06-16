package org.gaius.octopus.core.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gaius.octopus.common.model.BaseQuery;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源接口查询对象
 * @date 2024/6/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatasourceInterfaceQuery extends BaseQuery {
    
    /**
     * 接口名称
     */
    private String name;
    
    /**
     * 数据源ID
     */
    private Long datasourceId;
}
