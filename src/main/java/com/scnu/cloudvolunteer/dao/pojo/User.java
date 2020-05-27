package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer userId;

    private String openid;

    private Integer role;

    private String parentName;

    private String name;

    private Integer sex;

    private Integer section;

    private Integer grade;

    private String address;

    private String wechat;

    private String phone;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}