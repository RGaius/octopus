package org.gaius.octopus.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.gaius.octopus.common.model.Result;
import org.gaius.octopus.core.pojo.dto.DatasourceInterfaceDTO;
import org.gaius.octopus.core.pojo.query.DatasourceInterfaceQuery;
import org.gaius.octopus.core.pojo.vo.DatasourceInterfaceVO;
import org.gaius.octopus.core.service.DatasourceInterfaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源接口控制器
 *
 * @author zhaobo
 * @program octopus
 * @description 数据源接口控制器
 * @date 2024/6/7
 */
@RestController
@RequestMapping("/api/v1/datasource/interface")
public class DatasourceInterfaceController {
    
    @Resource
    private DatasourceInterfaceService datasourceInterfaceService;
    
    /**
     * 数据源参数校验
     *
     * @param dto 数据源参数
     * @return
     */
    @PostMapping("test")
    public Result<Object> available(@RequestBody DatasourceInterfaceDTO dto) throws Exception {
        return Result.success(datasourceInterfaceService.test(dto));
    }
    
    /**
     * 新增数据源接口
     */
    @PostMapping("save")
    public Result<Boolean> add(@RequestBody DatasourceInterfaceDTO dto) {
        return Result.success(datasourceInterfaceService.save(dto));
    }
    
    /**
     * 更新数据源接口
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody DatasourceInterfaceDTO dto) {
        return Result.success(datasourceInterfaceService.update(dto));
    }
    
    /**
     * 删除数据源接口
     */
    @PostMapping("delete")
    public Result<Boolean> delete(@RequestBody DatasourceInterfaceDTO dto) {
        return Result.success(datasourceInterfaceService.deleteById(dto));
    }
    
    /**
     * 数据源接口分页查询
     *
     * @param query
     */
    @GetMapping("page")
    public Result<Page<DatasourceInterfaceVO>> page(DatasourceInterfaceQuery query) {
        return Result.success(datasourceInterfaceService.pageByQuery(query));
    }
    
}
