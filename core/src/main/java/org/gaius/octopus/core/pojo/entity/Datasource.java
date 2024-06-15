package org.gaius.octopus.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源
 * @date 2024/6/10
 */
@Data
@TableName("datasource")
public class Datasource {
    
    @TableId
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
