package org.gaius.octopus.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gaius.datasource.Available;
import org.gaius.octopus.common.utils.JacksonUtil;
import org.gaius.octopus.core.execute.AbstractExecuteEngine;
import org.gaius.octopus.core.execute.ExecuteContext;
import org.gaius.octopus.core.execute.datasource.DatasourceExecuteDTO;
import org.gaius.octopus.core.mapper.DatasourceMapper;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.pojo.entity.Datasource;
import org.gaius.octopus.core.pojo.query.DatasourceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceVO;
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
public class DatasourceServiceImpl extends ServiceImpl<DatasourceMapper, Datasource> implements DatasourceService {
    
    @Resource
    private AbstractExecuteEngine<DatasourceExecuteDTO> datasourceExecuteEngine;
    
    @Override
    public Available test(DatasourceDTO dto) throws Exception {
        try {
            ExecuteContext<DatasourceExecuteDTO> executeContext = new ExecuteContext<>();
            executeContext.setContent(new DatasourceExecuteDTO(dto, null));
            return datasourceExecuteEngine.validate(executeContext);
        } catch (Exception e) {
            return Available.builder().available(false).message(e.getMessage()).build();
        }
    }
    
    @Override
    public DatasourceDTO selectById(Long datasourceId) {
        Datasource datasource = this.getById(datasourceId);
        if (datasource == null) {
            return null;
        }
        DatasourceDTO dto = new DatasourceDTO();
        dto.setId(datasource.getId());
        dto.setName(datasource.getName());
        dto.setType(datasource.getType());
        dto.setContent(JacksonUtil.parseToTargetObject(datasource.getContent()));
        return dto;
    }
    
    @Override
    public boolean save(DatasourceDTO dto) {
        // todo 判断数据源名称是否重复
        Datasource datasource = new Datasource();
        datasource.setName(dto.getName());
        datasource.setDescription(dto.getDescription());
        datasource.setType(dto.getType());
        datasource.setContent(JacksonUtil.writeObjectToString(dto.getContent()));
        Date date = new Date();
        datasource.setCreateTime(date);
        datasource.setUpdateTime(date);
        return this.save(datasource);
    }
    
    @Override
    public Boolean update(DatasourceDTO dto) {
        // todo 判断数据源名称是否重复
        Datasource datasource = new Datasource();
        datasource.setId(dto.getId());
        datasource.setName(dto.getName());
        datasource.setDescription(dto.getDescription());
        datasource.setType(dto.getType());
        datasource.setContent(JacksonUtil.writeObjectToString(dto.getContent()));
        datasource.setUpdateTime(new Date());
        // todo 数据源更新后发送消息通知销毁对应数据源实例
        return this.updateById(datasource);
    }
    
    @Override
    public boolean deleteById(DatasourceDTO dto) {
        // todo 判断数据源下是否有接口，若有接口，则不允许删除
        if (this.removeById(dto.getId())) {
            // todo 数据源删除后发送消息通知销毁对应数据源实例
            return true;
        }
        return false;
    }
    
    @Override
    public Page<DatasourceVO> pageByQuery(DatasourceQuery query) {
        Page<Datasource> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<Datasource> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(StringUtils.isNotEmpty(query.getName()), Datasource::getName, query.getName())
                .eq(StringUtils.isNotEmpty(query.getType()), Datasource::getType, query.getType());
        Page<Datasource> datasourcePage = this.page(page, lambdaQuery);
        Page<DatasourceVO> voPage = new Page<>();
        voPage.setCurrent(datasourcePage.getCurrent());
        voPage.setSize(datasourcePage.getSize());
        voPage.setTotal(datasourcePage.getTotal());
        voPage.setPages(datasourcePage.getPages());
        voPage.setRecords(datasourcePage.getRecords().stream().map(datasource -> {
            DatasourceVO vo = new DatasourceVO();
            vo.setId(datasource.getId());
            vo.setName(datasource.getName());
            vo.setType(datasource.getType());
            vo.setDescription(datasource.getDescription());
            vo.setCreateTime(datasource.getCreateTime());
            vo.setUpdateTime(datasource.getUpdateTime());
            return vo;
        }).collect(Collectors.toList()));
        return voPage;
    }
}
