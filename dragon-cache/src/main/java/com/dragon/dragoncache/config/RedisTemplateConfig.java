package com.dragon.dragoncache.config;

import com.dragon.dragoncommon.util.yml.YamlPropertyLoaderFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
* @Description: Redis 基础类配置
* @Author: zhangsong
* @Date: 2019/9/7
*/
@Slf4j
@Configuration
@EnableCaching
public class RedisTemplateConfig {

    @Resource
    MyRedisConfig myRedisConfig;

    @Bean("redisConnectionFactory")
    public RedisConnectionFactory getRedisConnectionFactory(){
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfigurationBuilder
                = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.poolConfig(new JedisPoolConfig());
        return new JedisConnectionFactory(getRedisConfiguration(),jedisClientConfigurationBuilder.build());
    }

    @Bean("redisCacheManager")
    public RedisCacheManager getRedisCacheManager(RedisConnectionFactory connectionFactory){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();

        log.info("redisCacheManager加载完成");
        return redisCacheManager;
    }
    private RedisStandaloneConfiguration getRedisConfiguration() {
        log.info("开始解析redis配置");
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        RedisServer master = myRedisConfig.getMaster();
        if (null != master.getPassword() && !"".equals(master.getPassword())) {
            redisConfiguration.setPassword(RedisPassword.of(master.getPassword()));
        } else {
            redisConfiguration.setPassword(RedisPassword.none());
        }
        redisConfiguration.setPort(master.getPort());
        redisConfiguration.setDatabase(master.getDatabase());
        return redisConfiguration;
    }

    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始加载redisTemplate");
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        log.info("redisTemplate加载结束");
        return redisTemplate;
    }

    private RedisSerializer valueSerializer(){
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }

}
