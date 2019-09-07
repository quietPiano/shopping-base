package com.dragon.dragoncache.config;

import lombok.Data;

/** 
* @Description: Redis服务器详细设置 
* @Author: zhangsong
* @Date: 2019/9/7 
*/ 
@Data
public class RedisServer {
    private int database;
    private String host;
    private String url;
    private String password;
    private int port;
    private long timeout;
    private int maxActive;
    private int maxWait;
    private int minIdle;
    private int maxIdle;
    private int timeBetweenEvictionRuns;
}
