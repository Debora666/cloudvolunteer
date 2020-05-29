package com.scnu.cloudvolunteer.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"updateDate","createDate"})
@Data
public class Volunteer implements Serializable {
    private Integer volunteerId;

    private String openid;

    private Integer status;

    private Integer role;

    private String name;

    private Integer sex;

    private Integer totalWorkTime;

    private Integer organization;

    private String college;

    private String major;

    private Integer grade;

    private String stuNo;

    private Integer subject;

    private Integer section;

    private Integer experience;

    private Integer serviceTime;

    private String speciality;

    private String wechat;

    private String phone;

    private String remark;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}