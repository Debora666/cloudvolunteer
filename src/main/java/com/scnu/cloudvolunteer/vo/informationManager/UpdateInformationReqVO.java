package com.scnu.cloudvolunteer.vo.informationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 更新个人信息请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInformationReqVO implements Serializable {
    // 公用
    private Integer id;
    private Integer role;
    // 用户
    private String parentName;
    private String address;
    // 志愿者
    private String college;
    private String major;
    private String stuNo;
    private Integer subject;
    private Integer experience;
    private Integer serviceTime;
    private Integer organization;
    private String speciality;
    private String remark;
    // 管理员
    private String password;
    // 用户和志愿者公用
    private String name;
    private Integer sex;
    private Integer grade;
    private Integer section;
    private String wechat;
    private String phone;
}
