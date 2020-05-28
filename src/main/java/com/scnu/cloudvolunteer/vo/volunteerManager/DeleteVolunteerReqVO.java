package com.scnu.cloudvolunteer.vo.volunteerManager;

import lombok.Data;


/**
 * @author     ：ben liang
 * @date       ：2020/5/27
 * @description：删除志愿者请求VO
 * @modified By：
 */
@Data
public class DeleteVolunteerReqVO {
    private Integer adminId;
    private Integer volunteerId;
}
