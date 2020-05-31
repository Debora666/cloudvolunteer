package com.scnu.cloudvolunteer.service.register;

import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.register.VolunteerRegisterReqVO;
import com.scnu.cloudvolunteer.vo.register.VolunteerRegisterResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘良鑫
 * @data 2020/6/1
 *
 */
@Service(SvcConstant.VOLUNTEER_REGISTER)
public class VolunteerRegisterService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(VolunteerRegisterService.class);

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<VolunteerRegisterService> responseVO = new ResponseVO<>();
        VolunteerRegisterResVO resVO = new VolunteerRegisterResVO();
        //把请求参数转化为实体类
        VolunteerRegisterReqVO reqVO = JsonUtil.string2Obj(request,VolunteerRegisterReqVO.class);

        //入参校验
        validation(reqVO);

        //志愿者数据插入数据库
        volunteerRegister(reqVO);
        return responseVO;
    }

    /**
     * 志愿者数据插入数据库
     * @param reqVO
     * @throws BaseException
     */

    private void volunteerRegister(VolunteerRegisterReqVO reqVO) throws BaseException{
        Volunteer volunteer = new Volunteer();
        try {
            volunteer.setVolunteerId(reqVO.getVolunteerId());
            volunteer.setName(reqVO.getName());
            volunteer.setSex(reqVO.getSex());
            volunteer.setOrganization(reqVO.getOrganization());
            volunteer.setCollege((reqVO.getCollege()));
            volunteer.setMajor(reqVO.getMajor());
            volunteer.setGrade(reqVO.getGrade());
            volunteer.setStuNo(reqVO.getStuNo());
            volunteer.setSubject(reqVO.getSubject());
            volunteer.setExperience(reqVO.getExperience());
            volunteer.setServiceTime(reqVO.getServiceTime());
            volunteer.setSpeciality(reqVO.getSpeciality());
            volunteer.setWechat(reqVO.getWechat());
            volunteer.setPhone(reqVO.getPhone());
            volunteer.setRemark(reqVO.getRemark());
            volunteerMapper.updateBySelected(volunteer);

        }catch (Exception e){
            logger.error("志愿者注册出错",e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }

    /**
     * 入参校验
     * @param reqVO
     * @throws BaseException
     */

    private void validation(VolunteerRegisterReqVO reqVO) throws BaseException {
        if (reqVO==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getVolunteerId()==null||reqVO.getName()==null||reqVO.getSex()==null||reqVO.getOrganization()==null||reqVO.getCollege()==null||reqVO.getMajor()==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getGrade()==null||reqVO.getStuNo()==null||reqVO.getSubject()==null||reqVO.getSection()==null||reqVO.getExperience()==null||reqVO.getServiceTime()==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getWechat()==null||reqVO.getPhone()==null)
        {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }



    }
}
