package com.scnu.cloudvolunteer.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：ben liang
 * @date ：2020/5/29
 * @description：查看用户的服务记录实体
 * @modified By：
 */
@Data
public class UserServiceRecord implements Serializable {
    private Integer serviceId;

    private Date createDate;

    private Date updateDate;

    private Integer serviceStatus;

    private Volunteer volunterr;

    private static final long serialVersionUID = 1L;
}
