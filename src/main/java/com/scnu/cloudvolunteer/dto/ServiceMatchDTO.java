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

    private Integer maxServiceNum;
    private Integer limitNum;
}
