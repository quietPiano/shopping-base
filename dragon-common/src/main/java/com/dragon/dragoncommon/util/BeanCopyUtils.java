package com.dragon.dragoncommon.util;

import java.util.Collections;
import java.util.List;

/**
 * @program: shopping-base
 * @description: 对象深度copy工具类
 * @author: zhangsong
 * @create: 2019-09-08 10:28
 **/
public class BeanCopyUtils {

    /**
     * 对象深度copy
     * @param obj
     * @param type
     * @param <T>
     * @return
     */
    public static<T> T copyBean(Object obj,Class<T> type){
        if (null == obj || null == type){
            return null;
        }
        String json = JsonUtils.toJson(obj);
        return JsonUtils.toBean(json,type);
    }

    /**
     * 深度copyList
     * @param fromList
     * @param type
     * @param <F>
     * @param <T>
     * @return
     */
    public static<F,T> List<T> copyList(List<F> fromList,Class<T> type){
        List<T> toList = Collections.emptyList();
        if (toList.isEmpty()){
            return toList;
        }
        fromList.stream().forEach(obj ->{
            toList.add(copyBean(obj,type));
        });
        return toList;
    }
}
