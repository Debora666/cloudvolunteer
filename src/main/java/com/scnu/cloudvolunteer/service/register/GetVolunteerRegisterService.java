package com.scnu.cloudvolunteer.service.register;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.constant.VolunteerStatusConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.RedisKeyUtil;
import com.scnu.cloudvolunteer.utils.RedisUtil;
import com.scnu.cloudvolunteer.utils.SpringUtil;
import com.scnu.cloudvolunteer.vo.register.GetVolunteerRegisterReqVO;
import com.scnu.cloudvolunteer.vo.register.GetVolunteerRegisterResVO;
import com.scnu.cloudvolunteer.vo.register.VolunteerRegisterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者注册
 */
@Service(SvcConstant.GET_VOLUNTEER_REGISTER)
public class GetVolunteerRegisterService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(GetVolunteerRegisterService.class);

    // 归属组织 的正则表达式
    private static String ORGANIZATION_PATTEN = "[1-4]\\d{3}";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<GetVolunteerRegisterResVO> responseVO = new ResponseVO<>();
        GetVolunteerRegisterResVO resVO = new GetVolunteerRegisterResVO();
        GetVolunteerRegisterReqVO reqVO = JsonUtil.string2Obj(request, GetVolunteerRegisterReqVO.class);
        // 入参校验
        validation(reqVO);

        List<Volunteer> volunteers;
        // 根据角色不同，执行不同
        try {
            if (reqVO.getRole() == RoleConstant.ADMIN2){
                volunteers = volunteerMapper.selectWaitCheckVolunteerByAdmin2(reqVO.getOrganization());
            }else {
                volunteers = volunteerMapper.selectAllWaitCheckVolunteer();
            }
        }catch (Exception e){
            logger.error("获取志愿者注册 数据库查询出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        // 重构志愿者信息
        List<VolunteerRegisterVO> volunteerRegisters = setTargetOrgToVO(volunteers);
        // 返回响应参数
        resVO.setVolunteers(volunteerRegisters);
        return responseVO;
    }

    /**
     * * 入参校验
     * @param reqVo
     * @throws BaseException
     */
    private void validation(GetVolunteerRegisterReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getAdminId() == null || reqVo.getRole() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getRole() != RoleConstant.ADMIN && reqVo.getRole() != RoleConstant.ADMIN2){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getRole() == RoleConstant.ADMIN2 && reqVo.getOrganization() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }

    /**
     * 修改志愿者信息
     * 如果志愿者状态为更改组织中，从redis中获取转入组织放进去
     * 如果为更改组织中，但redis的转入组织有问题，返回的志愿者状态改为通过
     *       但数据库仍为更改组织中，等前端传回转入组织
     * @param volunteers
     * @return
     * @throws BaseException
     */
    private List<VolunteerRegisterVO> setTargetOrgToVO(List<Volunteer> volunteers)throws BaseException{
        List<VolunteerRegisterVO> volunteerRegisters = new ArrayList<>(volunteers.size());
        for (Volunteer volunteer : volunteers) {
            VolunteerRegisterVO volunteerRegisterVO = new VolunteerRegisterVO();
            if (volunteer.getStatus() == VolunteerStatusConstant.CHANGE_ORGANIZATION_CHECKING){
                // 从缓存中获取转入组织
                String targetOrganization = redisUtil.getValue(
                        RedisKeyUtil.getChangeOrganizationKey(volunteer.getVolunteerId()));
                // 缓存中没有对应的转入组织
                if (!StringUtils.hasText(targetOrganization)){
                    // 修改返回的志愿者状态为通过
                    volunteer.setStatus(VolunteerStatusConstant.PASS);
                } else if (!targetOrganization.matches(ORGANIZATION_PATTEN)){  // 缓存中的转入组织格式不对
                    // 修改返回的志愿者状态为通过
                    volunteer.setStatus(VolunteerStatusConstant.PASS);
                    // 删除缓存
                    redisUtil.del(RedisKeyUtil.getChangeOrganizationKey(volunteer.getVolunteerId()));
                }else {
                    // 缓存格式正确，放进响应参数中
                    volunteerRegisterVO.setTargetOrganization(Integer.parseInt(targetOrganization));
                }
            }
            SpringUtil.copyPropertiesIgnoreNull(volunteer, volunteerRegisterVO);
            volunteerRegisters.add(volunteerRegisterVO);
        }
        return volunteerRegisters;
    }
}
