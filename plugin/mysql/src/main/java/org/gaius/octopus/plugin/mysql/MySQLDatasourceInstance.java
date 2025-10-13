package org.gaius.octopus.plugin.mysql;

import lombok.extern.slf4j.Slf4j;
import org.gaius.datasource.model.DatasourceProperties;
import org.gaius.octopus.plugin.jdbc.JdbcDatasourceInstance;

/**
 * mysql数据源
 *
 * @author gaius.zhao
 * @date 2024/5/23
 */
@Slf4j
public class MySQLDatasourceInstance extends JdbcDatasourceInstance {
    
    public MySQLDatasourceInstance(DatasourceProperties properties) {
        super(properties);
    }
}
