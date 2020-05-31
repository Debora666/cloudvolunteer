package com.scnu.cloudvolunteer.vo.serviceRecord;

import com.scnu.cloudvolunteer.dto.NocheckServiceDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：单条未审核服务信息的响应VO
 * @modified By：
 */
@Data
public class NocheckServiceRecordItemVO implements Serializable {
    private Integer volunteerId;
    private Integer orderServiceId;
    private String serviceOrderId;
    private Integer workTime;
    private Date createDate;

    public NocheckServiceRecordItemVO(String serviceOrderId, NocheckServiceDTO nocheckServiceDTO){
        volunteerId = nocheckServiceDTO.getVolunteerId();
        orderServiceId = nocheckServiceDTO.getOrderServiceId();
        this.serviceOrderId = serviceOrderId;
        workTime = nocheckServiceDTO.getWorkTime();
        createDate = nocheckServiceDTO.getCreateTime();
    }
}
