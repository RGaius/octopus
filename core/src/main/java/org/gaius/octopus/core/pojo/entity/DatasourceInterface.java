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
@TableName("datasource_interface")
public class DatasourceInterface {
    
    @TableId
    private Long id;
    
    /**
     * 数据源id
     */
    private Long datasourceId;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 数据源内容
     */
    private String content;
    
    /**
     * 参数
     */
    private String args;
    
    private Date createTime;
    
    private String createBy;
    
    private Date updateTime;
    
    private String updateBy;
    
}
