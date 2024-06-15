package org.gaius.octopus.core.controller;

import jakarta.annotation.Resource;
import org.gaius.datasource.Available;
import org.gaius.octopus.common.model.Result;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.service.DatasourceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源控制器
 *
 * @author zhaobo
 * @date 2024/6/7
 */
@RestController
@RequestMapping("/api/v1/datasource")
public class DatasourceController {
    
    @Resource
    private DatasourceService datasourceService;
    
    /**
     * 数据源参数校验
     *
     * @param dto 数据源参数
     * @return
     */
    @PostMapping("test")
    public Result<Available> available(@RequestBody DatasourceDTO dto) throws Exception {
        return Result.success(datasourceService.test(dto));
    }
    
    /**
     * 新增数据源
     */
    @PostMapping("save")
    public Result<Boolean> add(@RequestBody DatasourceDTO dto) {
        return Result.success(datasourceService.save(dto));
    }
    
    /**
     * 更新数据源
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody DatasourceDTO dto) {
        return Result.success(datasourceService.update(dto));
    }
    
    /**
     * 删除数据源
     */
    @PostMapping("delete")
    public Result<Boolean> delete(@RequestBody DatasourceDTO dto) {
        return Result.success(datasourceService.deleteById(dto));
    }
    
}
