package com.scnu.cloudvolunteer.vo.informationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看个人信息请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetInformationReqVO implements Serializable {
    private Integer id;
    private Integer role;
}
