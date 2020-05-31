package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘良鑫
 * @date :2020/6/1
 * 志愿者登录响应
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolunteerRegisterResVO implements Serializable {
}
