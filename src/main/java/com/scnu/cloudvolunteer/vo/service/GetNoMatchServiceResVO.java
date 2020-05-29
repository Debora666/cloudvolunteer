package com.scnu.cloudvolunteer.vo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.OrderService;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 获取未匹配服务响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetNoMatchServiceResVO implements Serializable {
    private List<OrderService> services;
}
