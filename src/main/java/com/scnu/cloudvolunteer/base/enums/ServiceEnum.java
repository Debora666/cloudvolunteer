package com.scnu.cloudvolunteer.base.enums;

import lombok.Getter;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 第三方服务错误code
 */
@Getter
public enum ServiceEnum {
    SERVICE_ERROR("C0001", "调用第三方服务出错"),

    DATABASE_ERROR("C0100", "数据库服务出错"),

    WECHAT_ERROR("C0200", "微信接口调用出错"),
    WECHAT_GET_ACCESSTOKEN_ERROR("C0201", "微信获取接口调用凭证出错"),
    WECHAT_LOGIN_ERROR("C0210", "微信登录出错"),
    WECHAT_LOGIN_CODE_ERROR("C0211", "微信登录code无效"),
    WECHAT_LOGIN_TIME_OUT("C0212", "微信登录超时"),
    WECHAT_SEND_TEMPLATE_ERROR("C0220", "微信发送模板消息出错"),
    JSON_TRANSFORM_ERROR("C0300", "Json数据转化出错"),
    ;

    private final String code;
    private final String message;

    ServiceEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
