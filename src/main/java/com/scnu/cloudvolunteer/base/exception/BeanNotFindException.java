package com.scnu.cloudvolunteer.base.exception;

import com.scnu.cloudvolunteer.base.enums.BaseEnum;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:31:56
 * @description：
 *  springUtil找不到类时的异常类
 */
public class BeanNotFindException extends BaseException {
    public BeanNotFindException(String code, String message) {
        super(code, message);
    }

    public BeanNotFindException(BaseEnum baseEnum) {
        super(baseEnum);
    }
}
