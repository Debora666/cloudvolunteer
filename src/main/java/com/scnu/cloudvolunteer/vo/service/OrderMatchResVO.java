package com.scnu.cloudvolunteer.vo.service;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 *  用户下单，系统匹配响应vo
 */
@Data
public class OrderMatchResVO implements Serializable {
    private Integer serviceStatus;
}
