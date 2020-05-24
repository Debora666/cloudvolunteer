package com.scnu.cloudvolunteer.base.enums;

import lombok.Getter;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:23:35
 * @description：
 *  enum基础通用类
 */
@Getter
public enum BaseEnum {
    ok("000000", "请求完成"),
    ERROR("PB0000", "未知异常！"),
    ACCESS_ERROR("PB0010", "获取accessToken未知异常"),
    NULL_REQUEST_ERROR("CSxxxx", "请求参数为空"),
    SVC_NULL("CS0010", "svc为空"),
    SVC_ERROR("CS0020", "svc错误"),
    SVC_VOID("CS0030", "svc无效")
    ;






    private String code;
    private String message;

    BaseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
