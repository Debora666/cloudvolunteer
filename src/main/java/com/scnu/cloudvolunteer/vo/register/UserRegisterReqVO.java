package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：liuliangxin
 * @date ：2020/6/1
 * 普通用户注册 请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterReqVO implements Serializable {
    private Integer userId;
    private String parentName;
    private String name;
    private Integer sex;
    private Integer section;
    private Integer grade;
    private String address;
    private String wechat;
    private String phone;
}
