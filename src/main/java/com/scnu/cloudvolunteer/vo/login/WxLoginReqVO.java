package com.scnu.cloudvolunteer.vo.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/26
 * 微信登录请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxLoginReqVO implements Serializable {
    private String code;
    private Integer role;


}
