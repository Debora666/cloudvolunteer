package com.scnu.cloudvolunteer.service.volunteerManager;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.volunteerManager.GetVolunteersReqVO;
import com.scnu.cloudvolunteer.vo.volunteerManager.GetVolunteersResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author     ：ben liang
 * @date       ：2020/5/27
 * @description：获取志愿者信息服务
 * @modified By：
 */
@Service(SvcConstant.GET_VOLUNTEERS)
public class GetVolunteers implements BaseService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {

        GetVolunteersReqVO reqVO = JsonUtil.string2Obj(request,GetVolunteersReqVO.class);
        validation(reqVO);
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        switch(reqVO.getRole()){
            case RoleConstant.ADMIN:
                volunteers = (ArrayList<Volunteer>)getAllAvailableVolunteers();
                break;
            case RoleConstant.ADMIN2:
                volunteers = (ArrayList<Volunteer>)getVolunteersByOrganization(reqVO);
                break;
        }
        GetVolunteersResVO resVO = new GetVolunteersResVO();
        resVO.setVolunteers(volunteers);

        ResponseVO<GetVolunteersResVO> resVOResponseVO = new ResponseVO<>();
        resVOResponseVO.setData(resVO);

        return resVOResponseVO;
    }

    /**
     * 入参检查
     * @param reqVO
     * @throws BaseException
     */
    private void validation(GetVolunteersReqVO reqVO)throws  BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId() == null || reqVO.getRole() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getRole() < RoleConstant.ADMIN || reqVO.getRole() > RoleConstant.ADMIN2) {
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        if (admin == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
    }

    /**
     * 获取系统中所有通过审核的志愿者
     * @return
     */
    private ArrayList<Volunteer> getAllAvailableVolunteers(){
        return (ArrayList<Volunteer>)volunteerMapper.selectAllAvailableVolunteers();
    }

    /**
     * 根据管理员所在的组织查询志愿者
     * @param reqVO
     * @return
     */
    private ArrayList<Volunteer> getVolunteersByOrganization(GetVolunteersReqVO reqVO){
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        return (ArrayList<Volunteer>)volunteerMapper.selectByOrganization(admin.getOrganization());
    }

}
