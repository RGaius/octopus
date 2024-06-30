package org.gaius.octopus.plugin.redis;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.gaius.datasource.Available;
import org.gaius.datasource.DatasourceInstance;
import org.gaius.datasource.InvokeContext;
import org.gaius.datasource.ServiceContext;
import org.gaius.datasource.exception.DatasourceException;
import org.gaius.datasource.model.DatasourceProperties;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;

import java.io.IOException;
import java.util.Map;

/**
 * Kafka 数据源
 *
 * @author gaius.zhao
 * @date 2024/5/23
 */
@Slf4j
public class RedisDatasourceInstance implements DatasourceInstance<Object> {
    
    /**
     * 数据源配置
     */
    private final DatasourceProperties properties;
    
    private RedissonClient redissonClient;
    
    /**
     * 测试连接成功
     */
    private static final String SUCCESS_TEMPLATE = "The %s connection is successful and cluster information is %s";
    
    public RedisDatasourceInstance(DatasourceProperties properties) {
        this.properties = properties;
    }
    
    @Override
    public DatasourceInstance<Object> init() {
        this.redissonClient = initClient(properties.getContent());
        return this;
    }
    
    private RedissonClient initClient(Map<String, Object> content) {
        Config config = getConfig(content);
        return Redisson.create(config);
    }
    
    @Override
    public Available available(ServiceContext context) throws Exception {
        RedissonClient client = null;
        try {
            client = initClient(properties.getContent());
            return Available.available(String.format(SUCCESS_TEMPLATE, properties.getDatasourceName(), client.getId()));
        } catch (Exception e) {
            log.error("redisng 连接失败", e);
            return Available.unavailable(e.getMessage());
        } finally {
            if (client != null) {
                redissonClient.shutdown();
                redissonClient = null;
            }
        }
    }
    
    @Override
    public Object invoke(InvokeContext context) throws DatasourceException, IOException {
        Map<String, Object> interfaceInfo = context.getInterfaceInfo();
        // string, set, list, hash, zset
        String type = MapUtils.getString(interfaceInfo, "type");
        // 获取操作类型 读或写
        String operation = MapUtils.getString(interfaceInfo, "operation");
        if ("read".equals(operation)) {
            if ("string".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                return redissonClient.getBucket(key, new StringCodec()).get();
            }
            if ("set".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                return redissonClient.getSet(key, new StringCodec()).readAll();
            }
            if ("list".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                return redissonClient.getList(key, new StringCodec()).readAll();
            }
            if ("hash".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                RMap<String, String> hashMap = redissonClient.getMap(key, new StringCodec());
                // 若value不为空，则读取某个key的值
                if (StringUtils.isNotBlank(value)) {
                    // 基于逗号分割
                    String[] values = value.split(",");
                    if (values.length == 1) {
                        return hashMap.get(value);
                    }
                    Map<String, String> resultMap = Maps.newHashMapWithExpectedSize(values.length);
                    for (String v : values) {
                        resultMap.put(v, hashMap.get(v));
                    }
                    return resultMap;
                }
                return hashMap.readAllMap();
            }
            if ("zset".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                return redissonClient.getScoredSortedSet(key, new StringCodec()).readAll();
            }
        }
        if ("write".equals(operation)) {
            if ("string".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                redissonClient.getBucket(key, new StringCodec()).set(value);
            }
            if ("set".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                redissonClient.getSet(key, new StringCodec()).add(value);
            }
            if ("list".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                redissonClient.getList(key, new StringCodec()).add(value);
            }
            if ("hash".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                redissonClient.getMap(key, new StringCodec()).put(key, value);
            }
            if ("zset".equals(type)) {
                String key = MapUtils.getString(interfaceInfo, "key");
                String value = MapUtils.getString(interfaceInfo, "value");
                //                redissonClient.getScoredSortedSet(key, new StringCodec()).add(value);
            }
        }
        
        return null;
    }
    
    @Override
    public void destroy() {
        redissonClient.shutdown();
        redissonClient = null;
    }
    
    /**
     * 获取redis连接配置
     *
     * @param content
     * @return
     */
    private Config getConfig(Map<String, Object> content) {
        String type = MapUtils.getString(content, "type");
        String address = MapUtils.getString(content, "address");
        String password = MapUtils.getString(content, "password");
        String username = MapUtils.getString(content, "username");
        Integer database = MapUtils.getInteger(content, "database");
        Config config = new Config();
        // 部署类型 single、cluster、sentinel
        if ("single".equals(type)) {
            // 单节点
            config.useSingleServer().setAddress(address).setPassword(password).setUsername(username)
                    .setDatabase(database);
        } else if ("cluster".equals(type)) {
            // 集群 基于逗号分隔
            String[] nodeAddress = StringUtils.split(address, ",");
            config.useClusterServers().addNodeAddress(nodeAddress).setPassword(password).setUsername(username)
                    .setReadMode(getReadMode(content));
        } else if ("sentinel".equals(type)) {
            // 哨兵
            String[] nodeAddress = StringUtils.split(address, ",");
            String masterName = MapUtils.getString(content, "masterName");
            config.useSentinelServers().setMasterName(masterName).addSentinelAddress(nodeAddress).setPassword(password)
                    .setDatabase(database).setUsername(username).setReadMode(getReadMode(content));
        }
        config.setCodec(new StringCodec());
        return config;
    }
    
    /**
     * 获取读模式
     *
     * @param content 配置
     * @return
     */
    private ReadMode getReadMode(Map<String, Object> content) {
        String readMode = MapUtils.getString(content, "readMode");
        if ("MASTER_SLAVE".equals(readMode)) {
            return ReadMode.MASTER_SLAVE;
        }
        if ("SLAVE".equals(readMode)) {
            return ReadMode.SLAVE;
        }
        return ReadMode.MASTER;
    }
}
