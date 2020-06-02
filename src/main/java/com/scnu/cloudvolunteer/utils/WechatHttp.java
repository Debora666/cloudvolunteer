package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.constant.AppConstant;
import com.scnu.cloudvolunteer.base.constant.UrlConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.http.BaseHttp;
import com.scnu.cloudvolunteer.vo.subscribeMessageVO.SendSubscribeMessagerReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzheng
 * @date 2020/05/25
 * @description：
 *  微信小程序接口调用
 */
public class WechatHttp extends BaseHttp {
    private static final Logger logger = LoggerFactory.getLogger(WechatHttp.class);
    /**
     * 微信登录
     * @param code
     * @return
     */
  public static Map code2Session(String code) throws BaseException{
      String response;
      try {
          Map<String, String> params = new HashMap<>(4);
          params.put("appid", AppConstant.WECHAT_KEY);
          params.put("secret", AppConstant.WECHAT_SECRET);
          params.put("js_code", code);
          params.put("grant_type", "authorization_code");
          response = doGet(UrlConstant.CODE2SESSION_URL, params);
      }catch (Exception e){
          logger.error("微信登录失败", e);
          throw new BaseException(ServiceEnum.WECHAT_LOGIN_ERROR);
      }
      if (response == null){
          throw new BaseException(ServiceEnum.WECHAT_LOGIN_ERROR);
      }
      Map<String, String> res =  JsonUtil.string2Obj(response, HashMap.class);
      if (res != null && res.containsKey("errcode") && ("40029".equals(res.get("errcode")) ||
              40029 == Integer.parseInt(res.get("errcode")))){
          throw new BaseException(ServiceEnum.WECHAT_LOGIN_ERROR);
      }
      return res;
  }

    /**
     * 获取accessToken,微信接口调用凭证
     * @return
     * @throws BaseException
     */
  public static Map getAccessToken() throws BaseException {
      Map<String, Object> params = new HashMap<>(3);
      params.put("grant_type", "client_credential");
      params.put("appid", AppConstant.WECHAT_KEY);
      params.put("secret", AppConstant.WECHAT_SECRET);
      String response = doGet(UrlConstant.ACCESS_TOKEN_URL, params);
      Map<String, String> map = JsonUtil.string2Obj(response, HashMap.class);
      if (map == null || map.containsKey("errcode")) {
          logger.warn("获取微信接口调用凭证错误");
      }
      return map;
  }

    /**
     * 发送订阅消息
     * @param accessToken
     * @param reqVO
     * @return
     * @throws BaseException
     * @modify by ben liang
     * @modifyDate 2020/06/1
     */
  public static Map sendTemplateMsg(String accessToken, SendSubscribeMessagerReqVO reqVO) throws BaseException {
      String requestUrl = UrlConstant.SEND_TEMPLATE_URL + accessToken;
      String params = JsonUtil.object2Json(reqVO);
      String respponse = doPost(requestUrl, params);
      Map<String, Object> map = JsonUtil.string2Obj(respponse, HashMap.class);
      if (map == null || (map.containsKey("errcode") && !(map.get("errcode") + "").equals("0")))
        throw new BaseException(ServiceEnum.WECHAT_SEND_TEMPLATE_ERROR.getCode(), "微信小程序发送模板消息api失败-错误码" + ((map == null) ? "" : map
            .get("errcode")));
      return map;
  }


}
