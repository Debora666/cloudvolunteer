package com.scnu.cloudvolunteer.base.exception;

import com.scnu.cloudvolunteer.base.enums.BaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:21:22
 * @description：
 *  所有异常类的基础父类
 *  以及通用基础异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
    private String code;
    private String message;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(BaseEnum baseEnum){
        super(baseEnum.getMessage());
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

}
