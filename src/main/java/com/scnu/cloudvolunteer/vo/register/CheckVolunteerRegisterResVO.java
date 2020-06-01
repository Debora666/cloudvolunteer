package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘良鑫
 * @date 2020/6/1
 *  审核志愿者服务 响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckVolunteerRegisterResVO implements Serializable {
}
