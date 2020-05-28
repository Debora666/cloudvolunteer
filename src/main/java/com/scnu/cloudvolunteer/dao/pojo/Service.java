package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Service implements Serializable {
    private Integer serviceId;

    private Integer orderServiceId;

    private Integer volunteerId;

    private Integer userId;

    private Integer serviceStatus;

    private Integer workTime;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}