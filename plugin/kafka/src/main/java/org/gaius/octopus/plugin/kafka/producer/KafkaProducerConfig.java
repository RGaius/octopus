package com.cloudwise.dovm.service.core.datasource.kafka.producer;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * Kafka生产者配置
 *
 * @author zhaobo
 * @date 2022/4/25
 */
@SuppressWarnings("unused")
@Data
public class KafkaProducerConfig extends GenericObjectPoolConfig<KafkaProducer<Object, Object>> {
    
    /**
     * 服务地址
     */
    private String boostrapServers;
    
    /**
     * 缓冲区内存大小
     */
    private Long bufferMemory;
    
    /**
     * 消息确认机制
     */
    private final String acks;
    
    /**
     * 重试次数
     */
    private Integer retries;
    
    /**
     * 键序列化类
     */
    private String keySerializer;
    
    /**
     * 值序列化类
     */
    private String valueSerializer;
    
    public KafkaProducerConfig(String boostrapServers, Long bufferMemory, String acks, Integer retries,
            String keySerializer, String valueSerializer) {
        this.boostrapServers = boostrapServers;
        this.bufferMemory = bufferMemory;
        this.acks = acks;
        this.retries = retries;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }
}
