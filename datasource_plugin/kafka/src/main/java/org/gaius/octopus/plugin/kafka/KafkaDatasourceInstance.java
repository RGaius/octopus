package org.gaius.octopus.plugin.kafka;

import com.cloudwise.dovm.service.core.datasource.kafka.producer.KafkaProducerConfig;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.gaius.datasource.Available;
import org.gaius.datasource.DatasourceInstance;
import org.gaius.datasource.InvokeContext;
import org.gaius.datasource.ServiceContext;
import org.gaius.datasource.exception.DatasourceException;
import org.gaius.datasource.model.DatasourceProperties;
import org.gaius.datasource.util.TemplateUtil;
import org.gaius.octopus.plugin.kafka.producer.KafkaProducerFactory;
import org.gaius.octopus.plugin.kafka.producer.KafkaProducerPool;

import java.util.Map;
import java.util.concurrent.Future;

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
    
    private KafkaProducerPool producerPool;
    
    /**
     * 测试连接成功
     */
    private static final String SUCCESS_TEMPLATE = "The %s connection is successful and cluster information is %s";
    
    public KafkaDatasourceInstance(DatasourceProperties properties) {
        this.properties = properties;
    }
    
    @Override
    public DatasourceInstance<Object> init() {
        Map<String, Object> content = properties.getContent();
        Map<String, Object> producer = (Map<String, Object>) MapUtils.getObject(content, "producer");
        String bootstrapServer = MapUtils.getString(content, "bootstrapService");
        Long bufferMemory = MapUtils.getLong(producer, "bufferMemory");
        String acks = MapUtils.getString(producer, "acks");
        Integer retries = MapUtils.getInteger(producer, "retries");
        String keySerializerClass = MapUtils.getString(producer, "keySerializerClass");
        String valueSerializerClass = MapUtils.getString(producer, "valueSerializerClass");
        KafkaProducerConfig config = new KafkaProducerConfig(bootstrapServer, bufferMemory, acks, retries,
                keySerializerClass, valueSerializerClass);
        this.producerPool = new KafkaProducerPool(new KafkaProducerFactory(config));
        return this;
    }
    
    @Override
    public Available available(ServiceContext context) {
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
    public Object invoke(InvokeContext context) throws DatasourceException {
        KafkaProducer<Object, Object> kafkaProducer = null;
        try {
            kafkaProducer = producerPool.borrowObject();
            Map<String, Object> interfaceInfo = context.getInterfaceInfo();
            Integer partition = MapUtils.getInteger(interfaceInfo, "partition");
            if (partition != null && partition < 1) {
                partition = null;
            }
            Map<String, Object> args = context.getArgs();
            String topic = TemplateUtil.render(MapUtils.getString(interfaceInfo, "topic"), args);
            String key = TemplateUtil.render(MapUtils.getString(interfaceInfo, "key"), args);
            String value = TemplateUtil.render(MapUtils.getString(interfaceInfo, "value"), args);
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(topic, partition, key, value);
            log.info("kafka 生产者 2.x 发送消息，topic:{} partition:{} key:{} value:{}", topic, partition, key, value);
            Map<String, Object> result = Maps.newHashMap();
            Integer finalPartition = partition;
            Future<RecordMetadata> metadataFuture = kafkaProducer.send(producerRecord, ((metadata, e) -> {
                if (e != null) {
                    log.error("kafka 生产者 发送消息失败 topic:{} partition:{} key:{}", topic, finalPartition, key, e);
                }
            }));
            RecordMetadata metadata = metadataFuture.get();
            log.info("kafka 生产者 2.x 发送消息成功，topic:{} partition:{} offset:{} timestamp:{}", metadata.topic(),
                    metadata.partition(), metadata.offset(), metadata.timestamp());
            result.put("topic", metadata.topic());
            result.put("partition", metadata.partition());
            result.put("offset", metadata.offset());
            result.put("timestamp", metadata.timestamp());
            return result;
        } catch (Exception e) {
            log.error("Kafka 连接失败", e);
            throw new DatasourceException(e.getMessage());
        } finally {
            producerPool.returnObject(kafkaProducer);
        }
    }
    
    @Override
    public void destroy() {
        producerPool.close();
        producerPool = null;
    }
}
