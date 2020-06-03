package com.scnu.cloudvolunteer.vo.login;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.scnu.cloudvolunteer.dao.pojo.Admin;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：Debora
 * @date ：2020/6/03
 * 管理员登录响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminLoginResVO implements Serializable{
    private Integer adminId;
    private Integer role;
    private Integer organization;
    private Admin admin;
}
