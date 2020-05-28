package com.scnu.cloudvolunteer.vo.admin2manager;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：查看所有二级管理员VO
 * @modified By：
 */
@Data
public class GetAllAdmin2ReqVO implements Serializable {
    private Integer adminId;
}
