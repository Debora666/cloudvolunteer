package com.scnu.cloudvolunteer.vo.serviceRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/29
 * @description：查看所有服务记录请求VO
 * @modified By：
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetServiceRecordReqVO implements Serializable {
    Integer id;
    Integer role;
}
