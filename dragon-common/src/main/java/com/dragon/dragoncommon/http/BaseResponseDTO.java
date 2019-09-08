package com.dragon.dragoncommon.http;

import com.dragon.dragoncommon.baseenums.ReturnCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import java.io.Serializable;

/**
 * @program: shopping-base
 * @description: http响应
 * @author: zhangsong
 * @create: 2019-09-07 22:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDTO<T extends BaseDTO> implements Serializable {
    private static final long serialVersionUID = -2978023916610222561L;

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMsg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 构建成功返回对象
     */
    public static BaseResponseDTO buildSuccess(){
        return build(ReturnCodeEnum.SUCCESS,null);
    }

    /**
     * 构建成功返回对象
     */
    public static<T extends BaseDTO> BaseResponseDTO buildSuccess(@Nullable T t){
        return build(ReturnCodeEnum.SUCCESS,t);
    }

    /**
     * 构建失败返回对象
     */
    public static BaseResponseDTO buildFail(){
        return build(ReturnCodeEnum.FAIL,null);
    }

    /**
     * 构建失败返回对象
     */
    public static<T extends BaseDTO> BaseResponseDTO buildFail(@Nullable T t){
        return build(ReturnCodeEnum.FAIL,t);
    }

    /**
     * 构建返回对象
     */
    public static BaseResponseDTO build(ReturnCodeEnum returnCodeEnum){
        return build(returnCodeEnum,null);
    }

    /**
     * 构建返回对象
     */
    public static<T extends BaseDTO> BaseResponseDTO build(ReturnCodeEnum returnCodeEnum, @Nullable T t){
        return build(returnCodeEnum.getReturnCode(),returnCodeEnum.getReturnMsg(),t);
    }

    /**
     * 构建返回对象
     */
    public static<T extends BaseDTO> BaseResponseDTO build(String returnCode,String returnMsg){
        return build(returnCode,returnMsg,null);
    }

    /**
     * 构建返回对象
     */
    public static<T extends BaseDTO> BaseResponseDTO build(String returnCode,String returnMsg, @Nullable T t){
        return new BaseResponseDTO(returnCode,returnMsg,t);
    }
}
