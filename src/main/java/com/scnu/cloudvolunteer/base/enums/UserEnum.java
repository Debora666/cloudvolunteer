package com.scnu.cloudvolunteer.base.enums;

import lombok.Getter;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 用户端错误code
 */
@Getter
public enum UserEnum {
    USER_ERROR("A0001", "用户端错误"),
    USER_LOGIN_ERROR("A0200", "用户登录错误"),

    USER_NOT_EXIST("A0201","管理员账号不存在"),
    REQUEST_PARAM_ERROR("A0300", "请求参数错误"),
    REQUEST_PARAM_NULL("A0301", "请求必填参数为空"),
    REQUEST_PARAM_RANGE_ERROE("A0302", "请求参数超出允许的范围"),
    REQUEST_PARAM_FORMAT_ERROE("A0303", "请求参数格式不匹配"),
    SVC_NULL("A0304", "请求头中未携带svc"),
    SVC_ERROR("A0305", "svc错误"),

    ;

    private final String code;
    private final String message;

    UserEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
