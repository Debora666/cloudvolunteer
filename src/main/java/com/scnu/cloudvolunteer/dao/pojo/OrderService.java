package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderService implements Serializable {
    private Integer orderServiceId;

    private Integer userId;

    private String subject;

    private Integer section;

    private Integer serviceTime;

    private String remark;

    private Integer serviceStatus;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}