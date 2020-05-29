package com.scnu.cloudvolunteer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 服务匹配dto
 */
@Data
public class ServiceMatchDTO implements Serializable {
    private Integer subject;
    private Integer section;
    private Integer serviceTime;

    // 每位志愿者允许的最多服务对象
    private Integer maxServiceNum;
    // 查询偏移量
    private Integer offset;
    // 查询返回条数
    private Integer limitNum;
}
