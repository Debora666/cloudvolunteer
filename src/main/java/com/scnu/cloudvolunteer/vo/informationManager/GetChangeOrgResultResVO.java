package com.scnu.cloudvolunteer.vo.informationManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者更改组织请审核结果 响应vo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetChangeOrgResultResVO implements Serializable {
    /**
     * 审核结果
     * -1  - 不通过     0- 审核中   1 - 通过
     */
    private Integer changeResult;

    // 通过时返回
    private Volunteer volunteer;
}
