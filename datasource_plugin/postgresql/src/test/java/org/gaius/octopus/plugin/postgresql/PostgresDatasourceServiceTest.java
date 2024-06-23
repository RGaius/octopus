package org.gaius.octopus.plugin.postgresql;

import org.gaius.datasource.Available;
import org.gaius.datasource.ServiceContext;
import org.gaius.datasource.model.DatasourceProperties;
import org.gaius.octopus.common.middle.CryptoService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("use-container")
class PostgresDatasourceServiceTest {
    
    private static PostgreSQLContainer postgresql;
    
    @BeforeAll
    static void initContainer() {
        postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.12"));
        postgresql.withPassword("123456");
        postgresql.withUsername("root");
        postgresql.withDatabaseName("test");
        postgresql.start();
    }
    
    @AfterAll
    static void tearDown() {
        postgresql.stop();
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void available_success_when_datasource_info_is_valid_then_return_available() {
        Integer postgresqlMappedPort = postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT);
        // 建构测试数据
        Map<String, Object> datasourceInfo = Map.of("host", postgresql.getHost(), "port", postgresqlMappedPort, "user",
                "root", "password", "123456", "database", "test", "driverClass", "com.mysql.cj.jdbc.Driver",
                "urlFormat",
                "jdbc:mysql://${host}:${port}/${database}?serverTimezone=UTC&characterEncoding=utf-8&allowPublicKeyRetrieval=true");
        DatasourceProperties datasourceProperties = new DatasourceProperties();
        datasourceProperties.setContent(datasourceInfo);
        datasourceProperties.setTenantId("110");
        datasourceProperties.setDatasourceId(1L);
        // 获取工具类
        PostgresDatasourceFactory factory = new PostgresDatasourceFactory();
        // 创建实例
        PostgresDatasourceInstance instance = factory.create(datasourceProperties);
        ServiceContext mock = mock(ServiceContext.class);
        when(mock.getCryptoService()).thenReturn(new DefaultCryptoService());
        Available available = instance.available(mock);
        Assertions.assertTrue(available.getAvailable(), "数据库连接失败");
    }
    
    // 密码不正确，测试结果为false
    @Test
    void available_when_network_unavailable_then_return_unavailable() {
        Integer postgresqlMappedPort = postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT);
        // 构建测试数据
        Map<String, Object> datasourceInfo = Map.of("host", postgresql.getHost(), "port", postgresqlMappedPort, "user",
                "root", "password", "123123", "database", "test", "driverClass", "com.mysql.cj.jdbc.Driver",
                "urlFormat",
                "jdbc:mysql://${host}:${port}/${database}?serverTimezone=UTC&characterEncoding=utf-8&allowPublicKeyRetrieval=true");
        DatasourceProperties datasourceProperties = new DatasourceProperties();
        datasourceProperties.setContent(datasourceInfo);
        datasourceProperties.setTenantId("110");
        datasourceProperties.setDatasourceId(2L);
        PostgresDatasourceFactory factory = new PostgresDatasourceFactory();
        PostgresDatasourceInstance instance = factory.create(datasourceProperties);
        ServiceContext mock = mock(ServiceContext.class);
        when(mock.getCryptoService()).thenReturn(new DefaultCryptoService());
        Assertions.assertFalse(instance.available(mock).getAvailable(), "数据库连接失败");
    }
    
    public static class DefaultCryptoService implements CryptoService {
        
        @Override
        public String encrypt(String content) {
            return content;
        }
        
        @Override
        public String decrypt(String content) {
            return content;
        }
    }
}