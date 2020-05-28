package com.scnu.cloudvolunteer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 系统匹配志愿者查询结果实体类
 */
@Data
public class MatchResultDTO implements Serializable {
    private Integer volunteerId;
    private Integer organization;
    private Integer num;
}
