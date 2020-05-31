package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者注册 请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVolunteerRegisterReqVO implements Serializable {
    private Integer adminId;
    private Integer role;
    private Integer organization;
}
