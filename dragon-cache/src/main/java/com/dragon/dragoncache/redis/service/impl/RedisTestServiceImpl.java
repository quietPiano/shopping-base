package com.dragon.dragoncache.redis.service.impl;

import com.dragon.dragoncache.redis.entiy.RedisTestEntry;
import com.dragon.dragoncache.redis.service.RedisTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RedisTestServiceImpl implements RedisTestService {
    @Override
    @Cacheable(cacheNames = "select",key="#var")
    public RedisTestEntry select(int var) {
        log.info("开始查询，参数为{}",var);
        RedisTestEntry entry = new RedisTestEntry(1);
        log.info("查询结束，返回值为{}", entry.toString());
        return entry;
    }

    @Override
    @Cacheable(cacheNames = "listQuery",key="#var")
    public List<RedisTestEntry> listQuery(int var) {
        log.info("开始查询，参数为{}",var);
        RedisTestEntry entry = new RedisTestEntry(1);
        log.info("查询结束，返回值为{}", entry.toString());
        List<RedisTestEntry> list = new ArrayList<>();
        list.add(entry);
        return list;
    }
}
