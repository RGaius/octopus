package org.gaius.octopus.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gaius.datasource.Available;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.pojo.entity.Datasource;
import org.gaius.octopus.core.pojo.query.DatasourceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceVO;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源接口
 * @date 2024/6/7
 */
public interface DatasourceService extends IService<Datasource> {
    
    /**
     * 数据源测试
     *
     * @param dto 数据源对象
     * @return
     */
    Available test(DatasourceDTO dto) throws Exception;
    
    /**
     * 根据id查询数据源
     *
     * @param datasourceId 数据源id
     * @return 数据源对象
     */
    DatasourceDTO selectById(Long datasourceId);
    
    /**
     * 新增数据源
     *
     * @param dto 数据源对象
     * @return
     */
    boolean save(DatasourceDTO dto);
    
    /**
     * 更新数据源
     *
     * @param dto 数据源对象
     * @return
     */
    Boolean update(DatasourceDTO dto);
    
    /**
     * 删除数据源
     *
     * @param dto 数据源对象
     * @return
     */
    boolean deleteById(DatasourceDTO dto);
    
    /**
     * 分页查询
     *
     * @param query 查询对象
     * @return
     */
    Page<DatasourceVO> pageByQuery(DatasourceQuery query);
}
