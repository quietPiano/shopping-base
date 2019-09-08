package com.dragon.dragoncommon.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: shopping-base
 * @description: http请求封装
 * @author: zhangsong
 * @create: 2019-09-07 22:38
 **/
@Data
public class BaseRequestDTO<T extends BaseDTO> implements Serializable {
    private static final long serialVersionUID = -1816715977692405989L;

    /**
     * 请求头
     */
    private BaseRequestHeaderDTO requestHeader;

    /**
     * 请求体
     */
    private T data;
}
