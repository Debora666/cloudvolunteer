package com.scnu.cloudvolunteer.vo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 人工匹配请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HandMatchReqVO implements Serializable {
    private Integer adminId;
    private Integer userId;
    private List<Integer> volunteerIds;
    private Integer orderServiceId;
}
