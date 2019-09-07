package com.dragon.dragoncache.config;

import com.dragon.dragoncommon.util.yml.YamlPropertyLoaderFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

import java.util.List;


@Configuration
@PropertySource( value = "classpath:application.yml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class MyRedisConfig{
    RedisServer master;
    List<RedisServer> slave;
}
