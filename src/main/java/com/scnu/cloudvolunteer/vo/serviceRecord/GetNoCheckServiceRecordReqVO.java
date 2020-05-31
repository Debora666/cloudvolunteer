package com.scnu.cloudvolunteer.vo.serviceRecord;

import lombok.Data;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：查看所有待审核的服务信息的请求VO
 * @modified By：
 */
@Data
public class GetNoCheckServiceRecordReqVO {
    private Integer adminId;

    private Integer role;

    private Integer organization;
}
