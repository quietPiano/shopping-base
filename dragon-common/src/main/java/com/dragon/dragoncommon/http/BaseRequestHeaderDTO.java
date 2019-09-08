package com.dragon.dragoncommon.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: shopping-base
 * @description: http请求头
 * @author: zhangsong
 * @create: 2019-09-07 22:42
 **/
@Data
public class BaseRequestHeaderDTO implements Serializable {
    private static final long serialVersionUID = -3586469245874600479L;

    /**
     * 请求流水
     */
    private String requestNo;

    /**
     * 请求时间
     */
    private long requestTime;

    /**
     * 请求来源
     */
    private String requestChannel;
}
