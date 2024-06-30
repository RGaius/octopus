package org.gaius.octopus.plugin.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * Kafka生产者对象池
 *
 * @author zhaobo
 * @date 2022/4/25
 */
@Slf4j
public class KafkaProducerPool {
    
    private final GenericObjectPool<KafkaProducer<Object, Object>> pool;
    
    public KafkaProducerPool(KafkaProducerFactory factory) {
        super();
        this.pool = new GenericObjectPool<>(factory, factory.getConfig());
    }
    
    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
    public KafkaProducer<Object, Object> borrowObject() throws Exception {
        return pool.borrowObject();
    }
    
    /**
     * 归还连接
     *
     * @param producer 生产者
     */
    public void returnObject(KafkaProducer<Object, Object> producer) {
        if (producer != null) {
            pool.returnObject(producer);
        }
    }
    
    public void close() {
        this.pool.close();
    }
}
