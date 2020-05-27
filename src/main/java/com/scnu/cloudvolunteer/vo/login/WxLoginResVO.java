package com.scnu.cloudvolunteer.vo.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/26
 * 微信登录响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxLoginResVO implements Serializable {

    private Integer id;

    private User user;

    private Volunteer volunteer;

}
