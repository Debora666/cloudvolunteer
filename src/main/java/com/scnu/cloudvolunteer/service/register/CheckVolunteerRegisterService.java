package com.scnu.cloudvolunteer.service.register;

import com.scnu.cloudvolunteer.base.constant.ServiceStatusConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.register.CheckVolunteerRegisterReqVO;
import com.scnu.cloudvolunteer.vo.register.CheckVolunteerRegisterResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘良鑫
 * @date :2020/6/1
 * 审核志愿者注册
 */
@Service(SvcConstant.CHECK_VOLUNTEER_REGISTER)
public class CheckVolunteerRegisterService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(CheckVolunteerRegisterService.class);

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<CheckVolunteerRegisterService> responseVO = new ResponseVO<>();
        CheckVolunteerRegisterResVO resVO = new CheckVolunteerRegisterResVO();
        //把请求参数转化为实体类
        CheckVolunteerRegisterReqVO reqVO = JsonUtil.string2Obj(request,CheckVolunteerRegisterReqVO.class);

        //入参校验
        validation(reqVO);
        //新建一个志愿者实体
        Volunteer volunteer = new Volunteer();
        if(reqVO.getResult()==1)
        {
            volunteer.setVolunteerId(reqVO.getVolunteerId());
            volunteer.setStatus(ServiceStatusConstant.FINISH_SERVICE);
        }
        if (reqVO.getResult()==0)
        {
            volunteer.setVolunteerId(reqVO.getVolunteerId());
            volunteer.setStatus(ServiceStatusConstant.FAIL_MATCH);

        }

        try {
            volunteerMapper.updateBySelected(volunteer);
        }catch (Exception e)
        {
            logger.error("审核志愿者注册数据库出错",e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        return responseVO;
    }

    /**
     * 入参校验
     * @param reqVO
     * @throws BaseException
     */

    private void validation(CheckVolunteerRegisterReqVO reqVO) throws BaseException {

        if (reqVO==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId()==null ||reqVO.getVolunteerId()==null||reqVO.getResult()==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }
}
