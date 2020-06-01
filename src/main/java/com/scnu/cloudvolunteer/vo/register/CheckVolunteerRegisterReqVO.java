package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘良鑫
 * @date 2020/6/1
 * 审核志愿者注册 请求VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckVolunteerRegisterReqVO implements Serializable {
    private Integer adminId;
    private Integer volunteerId;
    private Integer result;
    private String reason;
}
