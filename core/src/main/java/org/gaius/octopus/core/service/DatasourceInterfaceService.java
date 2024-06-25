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
public interface DatasourceInterfaceService extends IService<DatasourceInterface> {
    
    /**
     * 数据源测试
     *
     * @param dto 数据源接口对象
     * @return
     */
    Object test(DatasourceInterfaceDTO dto) throws Exception;
    
    boolean save(DatasourceInterfaceDTO dto);
    
    boolean update(DatasourceInterfaceDTO dto);
    
    boolean deleteById(DatasourceInterfaceDTO dto);
    
    Page<DatasourceInterfaceVO> pageByQuery(DatasourceInterfaceQuery query);
    
    DatasourceInterfaceVO selectById(Long interfaceId);
}
