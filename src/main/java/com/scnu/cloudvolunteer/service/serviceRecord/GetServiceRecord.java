package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.ServiceRecordMapper;
import com.scnu.cloudvolunteer.dao.UserMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.*;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.serviceRecord.GetServiceRecordReqVO;
import com.scnu.cloudvolunteer.vo.serviceRecord.GetServiceRecordResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/29
 * @description：查看所有服务记录
 * @modified By：
 */
@Service(SvcConstant.GET_SERVICE_RECORD)
public class GetServiceRecord implements BaseService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private ServiceRecordMapper serviceRecordMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        GetServiceRecordReqVO reqVO = JsonUtil.string2Obj(request,GetServiceRecordReqVO.class);
        validation(reqVO);
        GetServiceRecordResVO serviceRecordResVO = new GetServiceRecordResVO();

        if (reqVO.getRole() == RoleConstant.USER){
            //如果请求者为普通用户
            try{
                ArrayList<UserServiceRecord> userServiceRecordArrayList  = (ArrayList<UserServiceRecord>) serviceRecordMapper.selectByUserId(reqVO.getId());
                serviceRecordResVO.setUserServiceRecords(userServiceRecordArrayList);
            }catch (Exception e){
                throw new BaseException(ServiceEnum.DATABASE_ERROR);
            }

        }else if(reqVO.getRole() == RoleConstant.VOLUNTEER ){
            //如果请求者为志愿者
            try{
                ArrayList<VolunteerServiceRecord> volunteerServiceRecordArrayList  = (ArrayList<VolunteerServiceRecord>) serviceRecordMapper.selectByVolunteerId(reqVO.getId());
                serviceRecordResVO.setVolunteerServiceRecords(volunteerServiceRecordArrayList);
            }catch (Exception e){
                throw new BaseException(ServiceEnum.DATABASE_ERROR);
            }
        }
        ResponseVO<GetServiceRecordResVO> resVO = new ResponseVO<>();
        resVO.setData(serviceRecordResVO);
        return resVO;
    }

    private void validation(GetServiceRecordReqVO reqVO) throws BaseException{
        if (reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getId() == null ||reqVO.getRole() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(!(reqVO.getRole() == RoleConstant.USER||reqVO.getRole() == RoleConstant.VOLUNTEER)){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        //判断是否为空用户
        switch(reqVO.getRole()){
            case RoleConstant.USER:
                User user;
                try{
                    user =  userMapper.selectByPrimaryKey(reqVO.getId());
                }catch(Exception e){
                    throw  new BaseException(ServiceEnum.DATABASE_ERROR);
                }
                if(user == null){
                    throw new BaseException(UserEnum.USER_NOT_EXIST);
                }
            case RoleConstant.VOLUNTEER:
                Volunteer volunteer;
                try {
                    volunteer = volunteerMapper.selectByPrimaryKey(reqVO.getId());
                }catch (Exception e){
                    throw new BaseException(ServiceEnum.DATABASE_ERROR);
                }
                if(volunteer == null){
                    throw new BaseException(UserEnum.USER_NOT_EXIST);
                }
        }
    }
}
