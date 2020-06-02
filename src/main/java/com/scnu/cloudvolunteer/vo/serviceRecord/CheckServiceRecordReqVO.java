package com.scnu.cloudvolunteer.vo.serviceRecord;

import lombok.Data;

/**
 * @author ：ben liang
 * @date ：2020/6/2
 * @description：审核服务信息请求VO
 * @modified By：
 */
@Data
public class CheckServiceRecordReqVO {
    //管理员用户id
    private Integer adminId;
    //服务信息次序
    private String serviceOrderId;
    //志愿者id
    private Integer volunteerId;
    //审核结果
    private Integer result;
    //服务时长
    private Integer workTime;
    //不通过原因
    private String reason;
}
