package org.gaius.octopus.plugin.postgresql;

import lombok.extern.slf4j.Slf4j;
import org.gaius.datasource.model.DatasourceProperties;
import org.gaius.octopus.plugin.jdbc.JdbcDatasourceInstance;

/**
 * PostgreSQL数据源
 *
 * @author gaius.zhao
 * @date 2024/5/23
 */
@Slf4j
public class PostgresDatasourceInstance extends JdbcDatasourceInstance {
    
    public PostgresDatasourceInstance(DatasourceProperties properties) {
        super(properties);
    }
}
