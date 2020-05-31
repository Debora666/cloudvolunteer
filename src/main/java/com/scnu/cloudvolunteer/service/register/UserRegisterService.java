package com.scnu.cloudvolunteer.service.register;


import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.UserMapper;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.register.UserRegisterReqVO;
import com.scnu.cloudvolunteer.vo.register.UserRegisterResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者注册
 */
@Service(SvcConstant.USER_REGISTER)
public class UserRegisterService implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterService.class);


    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<UserRegisterService> responseVO = new ResponseVO<>();
        UserRegisterResVO resVO = new UserRegisterResVO();
        //把请求参数转化为实体类
        UserRegisterReqVO reqVO = JsonUtil.string2Obj(request,UserRegisterReqVO.class);
        //入参校验
        validation(reqVO);

        //用户数据插入数据库
        userRegister(reqVO);
        return responseVO;
    }

    /**+
     * 用户数据插入数据库
     * @param reqVO
     * @throws BaseException
     */
    private void userRegister(UserRegisterReqVO reqVO) throws BaseException{

        User user = new User();
        try {
            user.setUserId(reqVO.getUserId());
            user.setParentName(reqVO.getParentName());
            user.setName(reqVO.getName());
            user.setSection(reqVO.getSection());
            user.setGrade(reqVO.getGrade());
            user.setAddress(reqVO.getAddress());
            user.setWechat(reqVO.getWechat());
            user.setPhone(reqVO.getPhone());
            userMapper.updateBySelected(user);
        }catch (Exception e ){
            logger.error("用户注册出错",e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);

        }
    }


    /**
     * * 入参校验
     * @param reqVO
     * @throws BaseException
     */
    private void validation(UserRegisterReqVO reqVO) throws BaseException  {
        if (reqVO==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getUserId()==null||reqVO.getParentName()==null||reqVO.getName()==null||reqVO.getSex()==null||reqVO.getSection()==null||reqVO.getGrade()==null||reqVO.getWechat()==null||reqVO.getPhone()==null)
        {
            throw  new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }

    }
}
