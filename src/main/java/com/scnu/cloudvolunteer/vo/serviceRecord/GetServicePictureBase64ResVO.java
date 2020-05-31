package com.scnu.cloudvolunteer.vo.serviceRecord;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：查看某次服务信息的图片响应VO
 * @modified By：
 */
@Data
public class GetServicePictureBase64ResVO {
    private ArrayList<String> serviceRecordBase64;
}
