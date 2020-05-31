package com.scnu.cloudvolunteer.vo.volunteerManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/27
 * 查看志愿者请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVolunteersReqVO implements Serializable {

    private Integer adminId;

    private Integer role;

    private Integer organization;
}
