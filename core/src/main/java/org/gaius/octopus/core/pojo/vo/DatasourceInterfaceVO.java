package org.gaius.octopus.core.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源视图对象
 * @date 2024/6/16
 */
@Data
public class DatasourceInterfaceVO {
    private Long id;
    
    private Long datasourceId;
    
    private String datasourceName;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 数据源类型
     */
    private String type;
    
    /**
     * 插件名称
     */
    private String pluginName;
    
    /**
     * 数据源内容
     */
    private Map<String, Object> content;
    private Map<String, Object> args;
    
    private Date createTime;
    
    private String createBy;
    
    private Date updateTime;
    
    private String updateBy;
}
