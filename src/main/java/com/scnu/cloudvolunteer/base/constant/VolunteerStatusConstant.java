package com.scnu.cloudvolunteer.base.constant;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 志愿者审核状态常量
 */
public class VolunteerStatusConstant {
    // 不通过
    public static final int NO_PASS = -1;
    // 待审核
    public static final int CHECKING = 0;
    // 更改组织审核中
    public static final int CHANGE_ORGANIZATION_CHECKING = 1;
    // 通过
    public static final int PASS = 2;
}
