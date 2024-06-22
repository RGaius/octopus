package org.gaius.octopus.core.service;

import org.gaius.datasource.exception.DatasourceException;
import org.gaius.octopus.core.CoreApplicationTests;
import org.gaius.octopus.core.execute.AbstractExecuteEngine;
import org.gaius.octopus.core.execute.datasource.DatasourceExecuteDTO;
import org.gaius.octopus.core.pojo.dto.DatasourceDTO;
import org.gaius.octopus.core.pojo.dto.DatasourceInterfaceDTO;
import org.gaius.octopus.core.service.impl.DatasourceInterfaceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DatasourceInterfaceServiceTest extends CoreApplicationTests {
    
    @Mock
    DatasourceService datasourceService;
    
    @Mock
    AbstractExecuteEngine<DatasourceExecuteDTO> datasourceExecuteEngine;
    
    @InjectMocks
    DatasourceInterfaceServiceImpl datasourceInterfaceService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void test_when_datasource_exist_then_return_success() throws Exception {
        // 模拟数据源
        DatasourceDTO datasourceDTO = new DatasourceDTO();
        datasourceDTO.setId(1L);
        datasourceDTO.setContent(Collections.emptyMap());
        datasourceDTO.setType("MySQL");
        Mockito.doReturn(datasourceDTO).when(datasourceService).selectById(1L);
        // 模拟数据源实例执行结果
        Mockito.doReturn(new ArrayList<>()).when(datasourceExecuteEngine).invoke(Mockito.any());
        
        DatasourceInterfaceDTO dto = new DatasourceInterfaceDTO();
        dto.setDatasourceId(1L);
        dto.setName("test");
        dto.setContent(Map.of("sql", "select * from user"));
        Object object = datasourceInterfaceService.test(dto);
        Assertions.assertInstanceOf(ArrayList.class, object, "返回值类型错误");
    }
    
    // 当数据源不存在时，返回数据源异常
    @Test
    void test_when_datasource_not_exist_then_return_datasource_exception() {
        Mockito.doReturn(null).when(datasourceService).selectById(1L);
        DatasourceInterfaceDTO dto = new DatasourceInterfaceDTO();
        dto.setDatasourceId(1L);
        dto.setName("test");
        dto.setContent(Map.of("sql", "select * from user"));
        Assertions.assertThrowsExactly(DatasourceException.class, () -> datasourceInterfaceService.test(dto),
                "数据源不存在时未返回异常");
    }
}