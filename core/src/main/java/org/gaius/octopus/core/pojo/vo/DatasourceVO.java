package org.gaius.octopus.core.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源视图对象
 * @date 2024/6/16
 */
@Data
public class DatasourceVO {
    
    private Long id;
    
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
    private String content;
    
    private Date createTime;
    
    private String createBy;
    
    private Date updateTime;
    
    private String updateBy;
}
