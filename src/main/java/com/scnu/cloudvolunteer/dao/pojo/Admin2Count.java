package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：带有统计所管理的志愿者数量的二级管理员
 * @modified By：
 */
@Data
public class Admin2Count implements Serializable {
    private Integer adminId;

    private String account;

    private Integer role;

    private Integer organization;

    private Integer volunteerNum;
}
