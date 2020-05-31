package com.scnu.cloudvolunteer.vo.register;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：liuliangxin
 * @date ：2020/6/1
 * 普通用户注册响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterResVO implements Serializable {
}
