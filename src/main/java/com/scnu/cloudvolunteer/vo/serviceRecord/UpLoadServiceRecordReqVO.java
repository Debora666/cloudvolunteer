package com.scnu.cloudvolunteer.vo.serviceRecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/30
 * @description：上传服务信息请求VO
 * @modified By：
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpLoadServiceRecordReqVO implements Serializable {
    //志愿者id
    private Integer volunteerId;
    //serviceRecordId
    private Integer serviceRecordId;
    //服务日志
    private ArrayList<String> files;
    //服务时长
    private Integer workTime;
}
