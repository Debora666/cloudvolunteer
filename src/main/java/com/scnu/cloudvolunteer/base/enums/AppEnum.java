package com.scnu.cloudvolunteer.base.enums;

import lombok.Getter;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 系统端错误code
 */
@Getter
public enum AppEnum {
    APP_ERROR("B0001", "系统执行出错"),
    APP_TIME_OUT("B0100", "系统执行超时"),
    ;

    private final String code;
    private final String message;

    AppEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
