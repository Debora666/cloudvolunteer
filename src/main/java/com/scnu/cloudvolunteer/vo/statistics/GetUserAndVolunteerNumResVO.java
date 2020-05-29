package com.scnu.cloudvolunteer.vo.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看平台所有用户和志愿者的统计数 响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserAndVolunteerNumResVO implements Serializable {
    private Integer userNum;
    private Integer volunteerNum;
}
