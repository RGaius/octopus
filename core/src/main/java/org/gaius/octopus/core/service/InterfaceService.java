package org.gaius.octopus.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gaius.octopus.core.pojo.dto.DatasourceInterfaceDTO;
import org.gaius.octopus.core.pojo.entity.DatasourceInterface;
import org.gaius.octopus.core.pojo.query.DatasourceInterfaceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceInterfaceVO;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源接口
 * @date 2024/6/7
 */
public interface InterfaceService extends IService<DatasourceInterface> {
    
    /**
     * 数据源测试
     *
     * @param dto 数据源接口对象
     * @return
     */
    Object test(DatasourceInterfaceDTO dto) throws Exception;
    
    /**
     * 保存
     *
     * @param dto 数据源接口对象
     * @return
     */
    boolean save(DatasourceInterfaceDTO dto);
    
    /**
     * 更新
     *
     * @param dto 数据源接口对象
     * @return
     */
    
    boolean update(DatasourceInterfaceDTO dto);
    
    /**
     * 删除
     *
     * @param dto 数据源接口对象
     * @return
     */
    boolean deleteById(DatasourceInterfaceDTO dto);
    
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return
     */
    Page<DatasourceInterfaceVO> pageByQuery(DatasourceInterfaceQuery query);
    
    /**
     * 根据id查询
     *
     * @param interfaceId 接口id
     * @return
     */
    DatasourceInterfaceVO selectById(Long interfaceId);
    
    /**
     * 根据数据源id查询
     *
     * @param datasourceId 数据源id
     * @return
     */
    Long countByDatasourceId(Long datasourceId);
}
