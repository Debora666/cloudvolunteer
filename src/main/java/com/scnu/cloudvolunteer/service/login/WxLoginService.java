package com.scnu.cloudvolunteer.service.login;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;

import com.scnu.cloudvolunteer.dao.UserMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;

import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.WechatHttp;
import com.scnu.cloudvolunteer.vo.login.WxLoginReqVO;
import com.scnu.cloudvolunteer.vo.login.WxLoginResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.Map;


/**
 * @author ：zzheng
 * @date ：2020/5/26
 * 微信登录服务
 */
@Service(SvcConstant.WECHAT_LOGIN)
public class WxLoginService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(WxLoginService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<WxLoginResVO> responseVo = new ResponseVO<>();
        WxLoginResVO resVo = new WxLoginResVO();
        // 将json字符串请求参数转换为实体类
        WxLoginReqVO reqVO = JsonUtil.string2Obj(request, WxLoginReqVO.class);
        // 入参校验
        validation(reqVO);
        // 微信登录
        Map<String, String> httpRes = WechatHttp.code2Session(reqVO.getCode());
        // 根据角色调用不同方法
        if (RoleConstant.USER == reqVO.getRole()) {
            userLogin(httpRes, resVo);
        } else {
            volunteerLogin(httpRes, resVo);
        }
        // 构造响应参数
        responseVo.setData(resVo);
        return responseVo;
    }

    /**
     * * 入参校验
     *
     * @param reqVo
     * @throws BaseException
     */
    private void validation(WxLoginReqVO reqVo) throws BaseException {
        if (reqVo == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (!StringUtils.hasText(reqVo.getCode()) || reqVo.getRole() == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        // 只能普通用户或志愿者
        if (reqVo.getRole() > RoleConstant.VOLUNTEER) {
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
    }

    /**
     * 用户登录
     * 第一次登录把openid插入数据库，返回userId
     * 非第一次登录返回用户信息
     *
     * @param httpRes
     * @param resVO
     * @return
     * @throws BaseException
     */
    private void userLogin(Map<String, String> httpRes, WxLoginResVO resVO) throws BaseException {
        String openid = httpRes.get("openid");
        User user;
        try {
            user = userMapper.selectByOpenid(openid);
            // 第一次登录
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                user.setRole(RoleConstant.USER);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                user.setCreateDate(timestamp);
                user.setUpdateDate(timestamp);
                userMapper.insert(user);
                resVO.setId(user.getUserId());
                return;
            }
            // 非第一次登录,赋值响应参数
            resVO.setUser(user);
        } catch (Exception e) {
            logger.error("微信登录出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }

    /**
     * 志愿者登录
     *
     * @param httpRes
     * @param resVO
     * @throws BaseException
     */
    private void volunteerLogin(Map<String, String> httpRes, WxLoginResVO resVO) throws BaseException {
        String openid = httpRes.get("openid");
        Volunteer volunteer;
        try {
            volunteer = volunteerMapper.selectByOpenid(openid);
            // 第一次登录
            if (volunteer == null) {
                volunteer = new Volunteer();
                volunteer.setOpenid(openid);
                volunteer.setRole(RoleConstant.VOLUNTEER);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                volunteer.setCreateDate(timestamp);
                volunteer.setUpdateDate(timestamp);
                volunteerMapper.insert(volunteer);
                resVO.setId(volunteer.getVolunteerId());
                return;
            }
            // 非第一次登录,赋值响应参数
            resVO.setVolunteer(volunteer);
        } catch (Exception e) {
            logger.error("微信登录出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }
}




