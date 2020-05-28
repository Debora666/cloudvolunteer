package com.scnu.cloudvolunteer.vo.admin2manager;

import lombok.Data;
import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：创建二级管理员账号请求VO
 * @modified By：
 */
@Data
public class CreateAdmin2ReqVO implements Serializable {
    //一级管理员id
    private Integer adminId;
    //二级管理员账号
    private String account;
    //二级管理员密码
    private String password;
    //二级管理员归属组织
    private Integer organization;
}
