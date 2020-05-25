package com.scnu.cloudvolunteer.base.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:24:05
 * @description：
 *  所有请求的响应参数类
 *  最后将它转化为json返回前端
 */
@Data
public class ResponseVO<T> implements Serializable {
    private T data;
    private String code;
    private String message;
}
