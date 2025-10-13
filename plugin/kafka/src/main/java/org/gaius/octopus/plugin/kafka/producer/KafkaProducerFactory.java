package org.gaius.octopus.plugin.kafka.producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import com.cloudwise.dovm.service.core.datasource.kafka.producer.KafkaProducerConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhaobo
 * @date 2022/4/25
 */
public class KafkaProducerFactory extends BasePooledObjectFactory<KafkaProducer<Object, Object>> {
    
    private final com.cloudwise.dovm.service.core.datasource.kafka.producer.KafkaProducerConfig config;
    
    public KafkaProducerFactory(KafkaProducerConfig config) {
        this.config = config;
    }
    
    public KafkaProducerConfig getConfig() {
        return config;
    }
    
    @Override
    public KafkaProducer<Object, Object> create() {
        Map<String, Object> producerConfig = new HashMap<>(8);
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBoostrapServers());
        producerConfig.put(ProducerConfig.BUFFER_MEMORY_CONFIG, config.getBufferMemory());
        producerConfig.put(ProducerConfig.ACKS_CONFIG, Objects.toString(config.getAcks(), "all"));
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, config.getRetries());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getValueSerializer());
        return new KafkaProducer<>(producerConfig);
    }
    
    @Override
    public void destroyObject(PooledObject<KafkaProducer<Object, Object>> p) throws Exception {
        KafkaProducer<Object, Object> kafkaProducer = p.getObject();
        if (kafkaProducer != null) {
            kafkaProducer.close();
        }
    }
    
    
    @Override
    public boolean validateObject(PooledObject<KafkaProducer<Object, Object>> p) {
        return true;
    }
    
    @Override
    public PooledObject<KafkaProducer<Object, Object>> wrap(KafkaProducer<Object, Object> obj) {
        return new DefaultPooledObject<>(obj);
    }
}
