package com.scnu.cloudvolunteer.vo.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 用于查看志愿者注册
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolunteerRegisterVO extends Volunteer implements Serializable {
    // 转入组织
    private Integer targetOrganization;
}
