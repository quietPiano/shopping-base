package com.dragon.dragoncache;


import com.dragon.dragoncache.redis.service.RedisTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DragonCacheApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    RedisTemplate redisTemplate;

//    @Test
//    public void testRedisConfig(){
//        System.out.println(redisSentinelConfiguration.toString());
//    }


    @Test
    public void testRedisTemplate(){
        redisTemplate.opsForValue().set("1","2");
        System.out.println(redisTemplate.opsForValue().get("1"));
    }

    @Resource
    RedisTestService redisTestService;
    @Test
    public void testCacheable(){
        redisTestService.listQuery(1);
        redisTestService.listQuery(1);
        redisTestService.select(1);
        redisTestService.select(1);
    }

}
