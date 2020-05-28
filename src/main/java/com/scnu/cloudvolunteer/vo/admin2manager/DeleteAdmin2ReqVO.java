package com.scnu.cloudvolunteer.vo.admin2manager;

import lombok.Data;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：删除二级管理员账号请求VO
 * @modified By：
 */
@Data
public class DeleteAdmin2ReqVO {
    private Integer adminId;
    private Integer admin2Id;
}
