package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘良鑫
 * @data 2020/6/1
 * 志愿者注册 请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolunteerRegisterReqVO implements Serializable {
    private Integer volunteerId;
    private String name;
    private Integer sex;
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
}
