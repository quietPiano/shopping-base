package com.dragon.dragoncache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
* @Description: Redis工具类
* @Author: zhangsong
* @Date: 2019/9/7
*/
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    public void set(String key,Object obj){
        redisTemplate.opsForValue().set(key,obj);
    }

    public void set(String key,Object obj,long time){
        redisTemplate.opsForValue().set(key,obj,time);
    }

    public void expire(String key,long time){
        redisTemplate.expire(key,time, TimeUnit.MILLISECONDS);
    }

    public Object get(String key){
        return null == key ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean setnx(String key,Object value,long time){
        return setnx(key,value,time,TimeUnit.MILLISECONDS);
    }

    public boolean setnx(String key,Object value,long time,TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,time,timeUnit);
    }

}
