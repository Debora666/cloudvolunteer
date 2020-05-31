package com.scnu.cloudvolunteer.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：志愿者单次服务信息
 * @modified By：
 */
@Data
public class NocheckServiceDTO implements Serializable {

    private Integer volunteerId;

    private Integer serviceRecordId;

    private ArrayList<String> fileNameArrayList;

    private Integer workTime;

    private Timestamp createTime;

    private Integer orderServiceId;

    private Integer organization;

    private static final long serialVersionUID = 1L;
}
