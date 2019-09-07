package com.dragon.dragoncache.redis.service;



import com.dragon.dragoncache.redis.entiy.RedisTestEntry;

import java.util.List;

public interface RedisTestService {

    RedisTestEntry select(int var);
    List<RedisTestEntry> listQuery(int var);

}
