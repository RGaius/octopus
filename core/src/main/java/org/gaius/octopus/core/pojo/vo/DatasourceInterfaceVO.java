package org.gaius.octopus.core.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    /**
     * 接口ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    
    /**
     * 数据源ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
     * 数据源内容
     */
    private Map<String, Object> content;
    
    /**
     * 参数
     */
    private Map<String, Object> args;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private String createBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    private String updateBy;
}
