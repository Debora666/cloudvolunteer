package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author ：ben liang
 * @date ：2020/5/29
 * @description：查看志愿者的服务记录实体
 * @modified By：
 */
@Data
public class VolunteerServiceRecord {
    private Integer serviceId;

    private Date createDate;

    private Date updateDate;

    private User user;

    private static final long serialVersionUID = 1L;
}
