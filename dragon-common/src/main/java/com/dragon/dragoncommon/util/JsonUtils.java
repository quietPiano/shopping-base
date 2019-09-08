package com.dragon.dragoncommon.util;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.List;

/**
 * @program: shopping-base
 * @description: json工具类
 * @author: zhangsong
 * @create: 2019-09-08 10:25
 **/
public class JsonUtils {

    /**
     * json 转对象
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public static<T> T toBean(String json,Class<T> classType){
        return null == json ? null : JSON.parseObject(json,classType);
    }

    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        return null == obj ? null : JSON.toJSONString(obj);
    }

    /**
     * json 转list
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public static<T> List<T> toList(String json, Class<T> classType){
        return null == json ? Collections.emptyList() : JSON.parseArray(json,classType);
    }
}
