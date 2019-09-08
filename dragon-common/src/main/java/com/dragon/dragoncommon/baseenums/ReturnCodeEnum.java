package com.dragon.dragoncommon.baseenums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: shopping-base
 * @description: 返回信息
 * @author: zhangsong
 * @create: 2019-09-07 22:54
 **/
@Getter
@AllArgsConstructor
public enum ReturnCodeEnum {

    SUCCESS("0000","操作成功"),
    PROCESS("0001","请求处理中"),
    FAIL_2001("2001","重复请求"),
    FAIL_3001("3001","参数异常"),
    FAIL_4001("4001","业务异常"),
    FAIL_8001("8001","未知异常"),
    FAIL("9999","操作失败")
    ;

    private String returnCode;
    private String returnMsg;

}
