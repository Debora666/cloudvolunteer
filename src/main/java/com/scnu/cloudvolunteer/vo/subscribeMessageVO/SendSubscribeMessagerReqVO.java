package com.scnu.cloudvolunteer.vo.subscribeMessageVO;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author ：ben liang
 * @date ：2020/6/2
 * @description：调用微信发送订阅消息接口的请求VO
 * @modified By：
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSubscribeMessagerReqVO {
    //接口调用凭证
    private String accessToken;
    //接收者（用户）的 openid
    private String touser;
    //所需下发的订阅模板id
    private String templateId;
    //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
    private String page;
    //模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
    private Map<String,Map<String,String>> data;
    //跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
    private String miniprogramState;
    //进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
    private String lang;
}
