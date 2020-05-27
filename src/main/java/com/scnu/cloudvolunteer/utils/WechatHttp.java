package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.constant.AppConstant;
import com.scnu.cloudvolunteer.base.constant.UrlConstant;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.http.BaseHttp;
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
      try {
          Map<String, String> params = new HashMap<>(4);
          params.put("appid", AppConstant.WECHAT_KEY);
          params.put("secret", AppConstant.WECHAT_SECRET);
          params.put("js_code", code);
          params.put("grant_type", "authorization_code");
          String response = doGet(UrlConstant.CODE2SESSION_URL, params);
          return JsonUtil.string2Obj(response, HashMap.class);
      }catch (Exception e){
          // TODO
          logger.error("");
          throw new BaseException();
      }
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
      if (map == null || map.containsKey("errcode"))
        throw new BaseException();
      return map;
  }

    /**
     * 发送模板消息
     * @param accessToken
     * @param params
     * @return
     * @throws BaseException
     */
//  public static Map sendTemplateMsg(String accessToken, String params) throws BaseException {
//      String requestUrl = URLS.SEND_TEMPLATE_URL + accessToken;
//      String respponse = doPost(requestUrl, params);
//      Map<String, Object> map = JsonUtil.string2Obj(respponse, HashMap.class);
//      if (map == null || (map.containsKey("errcode") && !(map.get("errcode") + "").equals("0")))
//        throw new BaseException(ExceptionCode.API_EXCEPTION, "微信小程序发送模板消息api失败-错误码" + ((map == null) ? "" : map
//            .get("errcode")));
//      return map;
//  }
}
