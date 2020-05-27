package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Admin implements Serializable {
    private Integer adminId;

    private String account;

    private String password;

    private String organization;

    private Integer role;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}