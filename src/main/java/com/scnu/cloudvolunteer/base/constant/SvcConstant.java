package com.scnu.cloudvolunteer.base.constant;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:19:50
 * @description：
 *  服务码常量
 */
public class SvcConstant {
    private static final String REGISTER = "01";
    private static final String LOGIN = "02";
    private static final String VOLUNTEER_MANAGER = "03";
    private static final String ADMIN2_MANAGER = "04";
    private static final String SERVICE_MATCH = "05";
    private static final String STATISTICS = "06";
    private static final String INFORMATION_MANAGER = "07";
    private static final String SERVICE_RECORD = "08";

    /*----------------------------注册-----------------------------------*/
    // 普通用户注册
    public static final String USER_REGISTER = REGISTER + "01";
    // 志愿者注册
    public static final String VOLUNTEER_REGISTER = REGISTER + "02";
    // 查看志愿者注册
    public static final String GET_VOLUNTEER_REGISTER = REGISTER + "03";
    // 审核志愿者注册
    public static final String CHECK_VOLUNTEER_REGISTER = REGISTER + "04";

    /*----------------------------登录-----------------------------------*/
    // 微信登录
    public static final String WECHAT_LOGIN = LOGIN + "01";
    // 管理员登录
    public static final String ADMIN_LOGIN = LOGIN + "02";

    /*----------------------------志愿者管理-----------------------------------*/
    // 查看志愿者
    public static final String GET_VOLUNTEERS = VOLUNTEER_MANAGER + "01";
    // 删除志愿者
    public static final String DELETE_VOLUNTEER = VOLUNTEER_MANAGER + "02";

    /*----------------------------二级管理员管理-----------------------------------*/
    // 创建二级管理员账号
    public static final String CREATE_ADMIN2 = ADMIN2_MANAGER + "01";
    // 查看所有二级管理员
    public static final String GET_ALL_ADMIN2 = ADMIN2_MANAGER + "02";
    // 删除二级管理员账号
    public static final String DELETE_ADMIN2 = ADMIN2_MANAGER + "03";

    /*----------------------------服务匹配-----------------------------------*/
    // 用户下单系统匹配
    public static final String ORDER_SERVICE_AND_MATCH = SERVICE_MATCH + "01";
    // 查看所有未匹配服务订单
    public static final String GET_NO_MATCH_SERVICE = SERVICE_MATCH + "02";
    // 人工匹配
    public static final String HAND_MATCH = SERVICE_MATCH + "03";

    /*----------------------------统计-----------------------------------*/
    // 查看平台所有用户统计数
    public static final String GET_USER_STATISTICS = STATISTICS + "01";

    /*----------------------------个人信息管理-----------------------------------*/
    // 查看个人信息
    public static final String GET_USER_INFORMATION = INFORMATION_MANAGER + "01";
    // 修改个人信息
    public static final String UPDATE_USER_INFORMATION = INFORMATION_MANAGER + "02";
    // 查看志愿者更改组织审核结果
    public static final String GET_CHANGE_ORGANIZATION_RESULT = INFORMATION_MANAGER + "03";

    /*----------------------------服务记录-----------------------------------*/
    // 查看所有服务记录
    public static final String GET_SERVICE_RECORD = SERVICE_RECORD + "01";
    // 上传服务信息
    public static final String UPLOAD_SERVICE_RECORD = SERVICE_RECORD + "02";
    // 查看所有待审核的服务信息
    public static final String GET_NO_CHECK_SERVICE_RECORD = SERVICE_RECORD + "03";
    // 审核服务信息
    public static final String CHECK_SERVICE_RECORD = SERVICE_RECORD + "04";
}
