package com.scnu.cloudvolunteer.service.login;


import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;

import com.scnu.cloudvolunteer.dao.pojo.Admin;

import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.WechatHttp;
import com.scnu.cloudvolunteer.vo.login.AdminLoginReqVO;
import com.scnu.cloudvolunteer.vo.login.AdminLoginResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.DigestUtils;


import java.util.Map;

/**
 * @author :Debora
 * @date ：2020/6/3
 * 管理员登录服务
 */

@Service(SvcConstant.ADMIN_LOGIN)
public class AdminLoginService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginService.class);
    @Autowired
    private AdminMapper adminMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<AdminLoginResVO> responseVo = new ResponseVO<>();
        AdminLoginResVO resVo = new AdminLoginResVO();
        // 将json字符串请求参数转换为实体类
        AdminLoginReqVO reqVO = JsonUtil.string2Obj(request, AdminLoginReqVO.class);
        // 入参校验
        validation(reqVO);
        // 微信登录
        Map<String, String> httpRes = WechatHttp.code2Session(reqVO.getAccount());
        // 调用管理员登录方法
        adminLogin(httpRes, resVo);

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
    private void validation(AdminLoginReqVO reqVo) throws BaseException {
        if (reqVo == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (!StringUtils.hasText(reqVo.getAccount()) || reqVo.getAccount() == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }

    }


    /**
     * 管理员登录
     * @param httpRes
     * @param resVO
     * @throws BaseException
     */
    private void adminLogin(Map<String, String> httpRes, AdminLoginResVO resVO) throws BaseException{
        String account = httpRes.get("account");
        Admin admin;
        String password;
        try {
            //根据管理员账户查询管理员信息
            admin = adminMapper.selectByAccount(account);
            //从前端获取的密码进行MD5加密
            password=DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
            if(admin == null) {
                //没有该账户：抛出异常，管理员账户不存在
                throw new BaseException(UserEnum.USER_NOT_EXIST);
                //数据库中的密码和新加密的密码进行比较，若相等，则登录成功，否则抛出异常,管理员密码错误
            }else if (password.equals(admin.getPassword())){
                //成功登录，赋值响应参数
                resVO.setAdmin(admin);
            }else{
                throw new BaseException(UserEnum.USER_PASSWORD_ERROR);
            }

        }catch (Exception e){
            logger.error("微信登录出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }

}



