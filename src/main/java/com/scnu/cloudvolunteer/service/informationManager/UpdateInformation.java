package com.scnu.cloudvolunteer.service.informationManager;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.constant.VolunteerStatusConstant;
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
import com.scnu.cloudvolunteer.utils.RedisKeyUtil;
import com.scnu.cloudvolunteer.utils.RedisUtil;
import com.scnu.cloudvolunteer.utils.SpringUtil;
import com.scnu.cloudvolunteer.vo.informationManager.UpdateInformationReqVO;
import com.scnu.cloudvolunteer.vo.informationManager.UpdateInformationResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 更新个人信息
 */
@Service(SvcConstant.UPDATE_USER_INFORMATION)
public class UpdateInformation implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(UpdateInformation.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<UpdateInformationResVO> responseVO = new ResponseVO<>();
        UpdateInformationResVO resVO = new UpdateInformationResVO();
        UpdateInformationReqVO reqVO = JsonUtil.string2Obj(request, UpdateInformationReqVO.class);
        validation(reqVO);
        // 根据角色不同，调用不同方法
        try {
            switch (reqVO.getRole()){
                case RoleConstant.USER:
                    User user = new User();
                    user.setUserId(reqVO.getId());
                    SpringUtil.copyPropertiesIgnoreNull(reqVO, user);
                    userMapper.updateBySelected(user);
                    resVO.setUser(user);
                    break;
                case RoleConstant.VOLUNTEER:
                    Volunteer volunteer = new Volunteer();
                    volunteer.setVolunteerId(reqVO.getId());
                    SpringUtil.copyPropertiesIgnoreNull(reqVO, volunteer);
                    // 志愿者更改组织，需要管理员审核，审核前保持原来的组织
                    if (reqVO.getOrganization() != null){
                        // 把转入组织放进redis
                        // 查看所有志愿者注册时获取
                        redisUtil.setValue(RedisKeyUtil.getChangeOrganizationKey(reqVO.getId())
                                , reqVO.getOrganization() + "");
                        // 不更改组织，保留原组织
                        volunteer.setOrganization(null);
                        // 更改志愿者状态为 更改组织中
                        volunteer.setStatus(VolunteerStatusConstant.CHANGE_ORGANIZATION_CHECKING);
                    }
                    volunteerMapper.updateBySelected(volunteer);
                    resVO.setVolunteer(volunteer);
                    break;
                case RoleConstant.ADMIN2:
                case RoleConstant.ADMIN:
                    if (StringUtils.hasText(reqVO.getPassword())){
                        Admin admin = new Admin();
                        admin.setAdminId(reqVO.getId());
                        String passwordMD5 = DigestUtils.md5DigestAsHex(reqVO.getPassword().getBytes());
                        admin.setPassword(passwordMD5);
                        adminMapper.updateBySelected(admin);
                    }
                    break;
                default:
                    throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
            }
        }catch (Exception e){
            logger.error("修改个人信息出错,role:[{}],id:[{}]", reqVO.getRole(), reqVO.getId(), e);
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
    private void validation(UpdateInformationReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getId() == null || reqVo.getRole() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }

}
