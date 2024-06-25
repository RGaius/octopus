package org.gaius.octopus.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.gaius.octopus.core.pojo.entity.DatasourceInterface;
import org.gaius.octopus.core.pojo.query.DatasourceInterfaceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceInterfaceVO;

/**
 * @author zhaobo
 * @program octopus
 * @description
 * @date 2024/6/10
 */
public interface DatasourceInterfaceMapper extends BaseMapper<DatasourceInterface> {
    
    Page<DatasourceInterfaceVO> selectByPage(Page<DatasourceInterface> page,
            @Param("query") DatasourceInterfaceQuery query);
}
