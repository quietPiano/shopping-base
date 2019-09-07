package com.dragon.dragoncache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
* @Description: Redis key生成规则
* @Author: zhangsong
* @Date: 2019/9/7
*/
@Component
public class RedisKeyGenerator implements KeyGenerator {

    /**
    * @Description: Redis key生成规则
    * @Param:
    * @return:  key
    * @Author: zhangsong
    * @Date: 2019/9/7
    */
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName());
        sb.append(method.getName());
        sb.append(objects.toString());
        return sb;
    }
}
