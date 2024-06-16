package org.gaius.octopus.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gaius.datasource.exception.DatasourceException;
import org.gaius.octopus.common.utils.JacksonUtil;
import org.gaius.octopus.core.execute.AbstractExecuteEngine;
import org.gaius.octopus.core.execute.ExecuteContext;
import org.gaius.octopus.core.execute.datasource.DatasourceExecuteDTO;
import org.gaius.octopus.core.mapper.DatasourceInterfaceMapper;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.pojo.dto.DatasourceInterfaceDTO;
import org.gaius.octopus.core.pojo.entity.DatasourceInterface;
import org.gaius.octopus.core.pojo.query.DatasourceInterfaceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceInterfaceVO;
import org.gaius.octopus.core.service.DatasourceInterfaceService;
import org.gaius.octopus.core.service.DatasourceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author zhaobo
 * @program octopus
 * @description 数据源接口实现类
 * @date 2024/6/7
 */
@Service
@Slf4j
public class DatasourceInterfaceServiceImpl extends ServiceImpl<DatasourceInterfaceMapper, DatasourceInterface>
        implements DatasourceInterfaceService {
    
    @Resource
    private DatasourceService datasourceService;
    
    @Resource
    private AbstractExecuteEngine<DatasourceExecuteDTO> datasourceExecuteEngine;
    
    @Override
    public Object test(DatasourceInterfaceDTO dto) throws Exception {
        // 基于数据源ID获取数据源实例
        DatasourceDTO datasourceDTO = datasourceService.selectById(dto.getDatasourceId());
        if (datasourceDTO == null) {
            throw new DatasourceException("not found");
        }
        DatasourceExecuteDTO executeDTO = DatasourceExecuteDTO.builder().datasource(datasourceDTO)
                .interfaceInfo(dto.getContent()).build();
        ExecuteContext<DatasourceExecuteDTO> context = ExecuteContext.<DatasourceExecuteDTO>builder()
                .content(executeDTO).args(dto.getArgs()).build();
        return datasourceExecuteEngine.invoke(context);
    }
    
    @Override
    public boolean save(DatasourceInterfaceDTO dto) {
        // 校验数据源是否存在
        if (datasourceService.selectById(dto.getDatasourceId()) != null) {
            return false;
        }
        DatasourceInterface datasourceInterface = new DatasourceInterface();
        datasourceInterface.setName(dto.getName());
        datasourceInterface.setDescription(dto.getDescription());
        datasourceInterface.setContent(JacksonUtil.writeObjectToString(dto.getContent()));
        datasourceInterface.setDatasourceId(dto.getDatasourceId());
        datasourceInterface.setArgs(JacksonUtil.writeObjectToString(dto.getArgs()));
        Date date = new Date();
        datasourceInterface.setCreateTime(date);
        datasourceInterface.setUpdateTime(date);
        return this.save(datasourceInterface);
    }
    
    @Override
    public boolean update(DatasourceInterfaceDTO dto) {
        if (datasourceService.selectById(dto.getDatasourceId()) != null) {
            return false;
        }
        DatasourceInterface datasourceInterface = new DatasourceInterface();
        datasourceInterface.setId(dto.getId());
        datasourceInterface.setName(dto.getName());
        datasourceInterface.setDescription(dto.getDescription());
        datasourceInterface.setContent(JacksonUtil.writeObjectToString(dto.getContent()));
        datasourceInterface.setDatasourceId(dto.getDatasourceId());
        datasourceInterface.setArgs(JacksonUtil.writeObjectToString(dto.getArgs()));
        Date date = new Date();
        datasourceInterface.setCreateTime(date);
        return this.updateById(datasourceInterface);
    }
    
    @Override
    public boolean deleteById(DatasourceInterfaceDTO dto) {
        // todo 判断数据源下是否有接口，若有接口，则不允许删除
        if (this.removeById(dto.getId())) {
            // todo 数据源删除后发送消息通知销毁对应数据源实例
            return true;
        }
        return false;
    }
    
    @Override
    public Page<DatasourceInterfaceVO> pageByQuery(DatasourceInterfaceQuery query) {
        Page<DatasourceInterface> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<DatasourceInterface> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(StringUtils.isNotEmpty(query.getName()), DatasourceInterface::getName, query.getName())
                .eq(query.getDatasourceId() != null, DatasourceInterface::getDatasourceId, query.getDatasourceId());
        Page<DatasourceInterface> datasourceInterfacePage = this.page(page, lambdaQuery);
        Page<DatasourceInterfaceVO> voPage = new Page<>();
        voPage.setTotal(datasourceInterfacePage.getTotal());
        voPage.setCurrent(datasourceInterfacePage.getCurrent());
        voPage.setSize(datasourceInterfacePage.getSize());
        voPage.setRecords(datasourceInterfacePage.getRecords().stream().map(datasourceInterface -> {
            DatasourceInterfaceVO vo = new DatasourceInterfaceVO();
            vo.setId(datasourceInterface.getId());
            vo.setName(datasourceInterface.getName());
            vo.setDescription(datasourceInterface.getDescription());
            vo.setDatasourceName(datasourceInterface.getName());
            vo.setCreateTime(datasourceInterface.getCreateTime());
            vo.setUpdateTime(datasourceInterface.getUpdateTime());
            return vo;
        }).collect(Collectors.toList()));
        return voPage;
    }
}
