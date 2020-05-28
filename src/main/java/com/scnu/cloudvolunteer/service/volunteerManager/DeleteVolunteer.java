package com.scnu.cloudvolunteer.service.volunteerManager;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.volunteerManager.DeleteVolunteerReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author     ：ben liang
 * @date       ：2020/5/27
 * @description：删除志愿者服务
 * @modified By：
 */
@Service(SvcConstant.DELETE_VOLUNTEER)
public class DeleteVolunteer implements BaseService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private AdminMapper adminMapper;

    private static final Logger logger = LoggerFactory.getLogger(DeleteVolunteer.class);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVO service(String request) throws BaseException {

        DeleteVolunteerReqVO reqVO = new DeleteVolunteerReqVO();
        reqVO = JsonUtil.string2Obj(request, DeleteVolunteerReqVO.class);
        validation(reqVO);

        ResponseVO<Integer> responseVO = new ResponseVO<>();
        Volunteer volunteer = volunteerMapper.selectByPrimaryKey(reqVO.getVolunteerId());
        int delcount = 0;
        try {
            delcount = volunteerMapper.deleteByPrimaryKey(reqVO.getVolunteerId());

        }catch (Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        if (delcount != 1) {
            throw new BaseException(AppEnum.APP_ERROR);
        }
        logger.warn("Admin("+reqVO.getAdminId()+") 删除了一名志愿者:"+volunteer.toString());
        return responseVO;
    }

    /**
     * 入参检查
     * @param reqVO
     * @throws BaseException
     */
    private void validation(DeleteVolunteerReqVO reqVO)throws  BaseException {
        if (reqVO == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVO.getAdminId() == null || reqVO.getVolunteerId() == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());

        Volunteer volunteer = volunteerMapper.selectByPrimaryKey(reqVO.getVolunteerId());
        if (admin == null || volunteer == null) {
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        privilegeValidation(admin, volunteer);
    }

    private void privilegeValidation(Admin admin, Volunteer volunteer) throws BaseException{
        //检查是否为管理员
        if (admin.getRole() < RoleConstant.ADMIN || admin.getRole() > RoleConstant.ADMIN2) {
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        //查看是否有权限删除
        if (admin.getRole() == RoleConstant.ADMIN2 && admin.getOrganization() != volunteer.getOrganization()) {
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
    }

}
