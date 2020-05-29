package com.scnu.cloudvolunteer.vo.informationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 更新个人信息响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInformationResVO implements Serializable {
    // 用户
    private User user;
    // 志愿者
    private Volunteer volunteer;
    // 管理员
    private Admin admin;
}
