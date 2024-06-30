package org.gaius.octopus.plugin.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.gaius.datasource.Available;
import org.gaius.datasource.DatasourceInstance;
import org.gaius.datasource.InvokeContext;
import org.gaius.datasource.ServiceContext;
import org.gaius.datasource.exception.DatasourceException;
import org.gaius.datasource.model.DatasourceProperties;

import java.io.IOException;
import java.util.Map;

/**
 * Kafka 数据源
 *
 * @author gaius.zhao
 * @date 2024/5/23
 */
@Slf4j
public class KafkaDatasourceInstance implements DatasourceInstance<Object> {
    
    /**
     * 数据源配置
     */
    private final DatasourceProperties properties;
    
    /**
     * 测试连接成功
     */
    private static final String SUCCESS_TEMPLATE = "The %s connection is successful and cluster information is %s";
    
    public KafkaDatasourceInstance(DatasourceProperties properties) {
        this.properties = properties;
    }
    
    @Override
    public DatasourceInstance<Object> init() {
        return this;
    }
    
    @Override
    public Available available(ServiceContext context) throws Exception {
        try {
            // 基于数据源配置，
            Map<String, Object> content = properties.getContent();
            KafkaAdminClient client = (KafkaAdminClient) KafkaAdminClient.create(content);
            // 测试连接
            DescribeClusterResult clusterResult = client.describeCluster();
            return Available.available(String.format(SUCCESS_TEMPLATE, properties.getDatasourceName(),
                    clusterResult.nodes().get().toString()));
        } catch (Exception e) {
            log.error("Kafka 连接失败", e);
            return Available.unavailable(e.getMessage());
        }
    }
    
    @Override
    public Object invoke(InvokeContext context) throws DatasourceException, IOException {
        return null;
    }
    
    @Override
    public void destroy() {
    
    }
}
