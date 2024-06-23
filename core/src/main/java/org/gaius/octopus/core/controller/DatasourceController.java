package org.gaius.octopus.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.gaius.datasource.Available;
import org.gaius.octopus.common.model.Result;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.pojo.query.DatasourceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceVO;
import org.gaius.octopus.core.service.DatasourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/datasource/v1")
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
     * 获取数据源详情
     */
    @GetMapping("/{datasourceId}")
    public Result<DatasourceDTO> detail(@PathVariable Long datasourceId) {
        return Result.success(datasourceService.selectById(datasourceId));
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
    
    /**
     * 数据源分页查询
     *
     * @param query
     */
    @GetMapping("page")
    public Result<Page<DatasourceVO>> page(DatasourceQuery query) {
        return Result.success(datasourceService.pageByQuery(query));
    }
}
