package com.scnu.cloudvolunteer.vo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 *  用户下单，系统匹配请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderMatchReqVO implements Serializable {
    private Integer userId;
    private Integer subject;
    private Integer section;
    private Integer serviceTime;
    private String remark;
}
