package com.dragon.dragoncache.config;

import com.dragon.dragoncommon.properties.YamlPropertyLoaderFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
* @Description:  Redis配置类
* @Author: zhangsong
* @Date: 2019/9/7
*/
@Configuration
@PropertySource( value = "classpath:application.yml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class MyRedisConfig{
    RedisServer master;
    List<RedisServer> slave;
}
