package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者注册 响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVolunteerRegisterResVO implements Serializable {
    List<VolunteerRegisterVO> volunteers;
}
