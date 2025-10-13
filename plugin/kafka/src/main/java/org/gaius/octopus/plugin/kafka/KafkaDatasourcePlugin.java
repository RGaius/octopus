package org.gaius.octopus.plugin.kafka;

import com.google.auto.service.AutoService;
import org.gaius.datasource.plugin.DatasourcePlugin;
import org.gaius.datasource.plugin.PluginContext;

/**
 * Kafka插件
 *
 * @author gaius.zhao
 * @date 2024/6/7
 */
@AutoService(DatasourcePlugin.class)
public class KafkaDatasourcePlugin implements DatasourcePlugin {
    
    @Override
    public String getName() {
        return "Kafka";
    }
    
    @Override
    public void load(PluginContext context) {
        // 注册当前插件
        context.getPluginService().register(getName(), new KafkaDatasourceFactory());
    }
}
