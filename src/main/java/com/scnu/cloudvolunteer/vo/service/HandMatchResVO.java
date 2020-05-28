package com.scnu.cloudvolunteer.vo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 人工匹配请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HandMatchResVO implements Serializable {

}
