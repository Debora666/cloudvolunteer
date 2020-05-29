package com.scnu.cloudvolunteer.service.informationManager;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.UserMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.informationManager.GetInformationReqVO;
import com.scnu.cloudvolunteer.vo.informationManager.GetInformationResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看个人信息
 * 所有角色都可以查看相应的信息
 */
@Service(SvcConstant.GET_USER_INFORMATION)
public class GetInformation implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(GetInformation.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<GetInformationResVO> responseVO = new ResponseVO<>();
        GetInformationResVO resVO = new GetInformationResVO();
        GetInformationReqVO reqVO = JsonUtil.string2Obj(request, GetInformationReqVO.class);
        validation(reqVO);
        // 根据角色不同，调用不同方法
        try {
            switch (reqVO.getRole()){
                case RoleConstant.USER:
                    User user = userMapper.selectByPrimaryKey(reqVO.getId());
                    resVO.setUser(user);
                    break;
                case RoleConstant.VOLUNTEER:
                    Volunteer volunteer = volunteerMapper.selectByPrimaryKey(reqVO.getId());
                    resVO.setVolunteer(volunteer);
                    break;
                case RoleConstant.ADMIN2:
                case RoleConstant.ADMIN:
                    Admin admin = adminMapper.selectByPrimaryKey(reqVO.getId());
                    resVO.setAdmin(admin);
                    int volunteerNum;
                    if (reqVO.getRole() == RoleConstant.ADMIN2){
                        volunteerNum = volunteerMapper.selectByOrganization(admin.getOrganization()).size();
                    }else {
                        volunteerNum = volunteerMapper.selectVolunteerCount();
                    }
                    resVO.setVolunteerNum(volunteerNum);
                    break;
                default:
                    throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
            }
        }catch (Exception e){
            logger.error("查看个人信息出错,role:[{}],id:[{}]", reqVO.getRole(), reqVO.getId(), e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        responseVO.setData(resVO);
        return responseVO;
    }

    /**
     * * 入参校验
     * @param reqVo
     * @throws BaseException
     */
    private void validation(GetInformationReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getId() == null || reqVo.getRole() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }

}
