package com.scnu.cloudvolunteer.vo.serviceRecord;

import lombok.Data;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：查看某次服务信息的图片请求VO
 * @modified By：
 */
@Data
public class GetServicePictureBase64ReqVO {
    private String serviceOrderId;
}
