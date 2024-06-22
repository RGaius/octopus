package org.gaius.octopus.core.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    /**
     * 主键 转字符串
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 接口数量
     */
    private Integer interfaceNumber;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 数据源类型
     */
    private String type;
    
    /**
     * 数据源内容
     */
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private String createBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    private String updateBy;
}
