package com.scnu.cloudvolunteer.base.enums;

import lombok.Getter;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:23:35
 * 基础code
 */
@Getter
public enum BaseEnum {
    ok("00000", "成功"),
    ERROR("99999", "未知异常"),

    ;

    private final String code;
    private final String message;

    BaseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
