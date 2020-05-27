package com.scnu.cloudvolunteer.base.exception;

import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.enums.BaseEnum;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
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

    public BaseException(){
        super();
    }

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

    public BaseException(UserEnum userEnum){
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
        this.message = userEnum.getMessage();
    }

    public BaseException(AppEnum appEnum){
        super(appEnum.getMessage());
        this.code = appEnum.getCode();
        this.message = appEnum.getMessage();
    }

    public BaseException(ServiceEnum serviceEnum){
        super(serviceEnum.getMessage());
        this.code = serviceEnum.getCode();
        this.message = serviceEnum.getMessage();
    }

}
