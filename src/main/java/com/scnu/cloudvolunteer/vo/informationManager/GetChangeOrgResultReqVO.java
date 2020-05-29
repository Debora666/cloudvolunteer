package com.scnu.cloudvolunteer.vo.informationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者更改组织请审核结果 请求vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetChangeOrgResultReqVO implements Serializable {
    private Integer volunteerId;
    private Integer targetOrganization;
}
