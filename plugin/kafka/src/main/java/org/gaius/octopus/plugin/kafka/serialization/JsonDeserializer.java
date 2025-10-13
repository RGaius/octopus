package org.gaius.octopus.plugin.kafka.serialization;

import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author zhaobo
 * @program octopus
 * @description
 * @date 2024/6/29
 */
public class JsonDeserializer implements Deserializer<String> {
    
    @Override
    public String deserialize(String topic, byte[] data) {
        return null;
    }
}
