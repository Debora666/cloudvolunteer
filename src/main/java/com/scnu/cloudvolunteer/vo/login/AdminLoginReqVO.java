package com.scnu.cloudvolunteer.vo.login;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：Debora
 * @date ：2020/6/03
 * 管理员登录请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminLoginReqVO implements Serializable{
    private String account;
    private  String password;
}
