package com.scnu.cloudvolunteer.base.constant;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:20:17
 * @description：
 *  url的常量类
 */
public class UrlConstant {
    /**
     * 微信小程序登录url
     * get
     */
    public static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * 微信小程序获取accessToken的url
     * get
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * 发送订阅消息url
     * post
     */
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
    /**
     * 小程序云开发 数据库插入的url
     * post
     */
    public static final  String DB_INSERT_URL= "https://api.weixin.qq.com/tcb/databaseadd?access_token=";
    /**
     * 小程序云开发 数据库更新的url
     * post
     */
    public static final  String DB_UPDATE_URL= "https://api.weixin.qq.com/tcb/databaseupdate?access_token=";
    /**
     * 小程序云开发 数据库删除的url
     * post
     */
    public static final  String DB_DELETE_URL= "https://api.weixin.qq.com/tcb/databasedelete?access_token=";
}
