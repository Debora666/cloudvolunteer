package com.scnu.cloudvolunteer.vo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 获取未匹配服务请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetNoMatchServiceReqVO implements Serializable {

}
