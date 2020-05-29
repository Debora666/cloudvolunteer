package com.scnu.cloudvolunteer.service.informationManager;

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
import com.scnu.cloudvolunteer.vo.informationManager.GetChangeOrgResultReqVO;
import com.scnu.cloudvolunteer.vo.informationManager.GetChangeOrgResultResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看志愿者更改组织的审核结果
 */
@Service(SvcConstant.GET_CHANGE_ORGANIZATION_RESULT)
public class GetChangeOrgResultService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(GetChangeOrgResultService.class);

    // 通过
    private final int NO_PASS = -1;
    // 审核中
    private final int WAIT_CHECK = 0;
    // 通过
    private final int PASS = 1;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<GetChangeOrgResultResVO> responseVO = new ResponseVO<>();
        GetChangeOrgResultResVO resVO = new GetChangeOrgResultResVO();
        GetChangeOrgResultReqVO reqVO = JsonUtil.string2Obj(request, GetChangeOrgResultReqVO.class);
        // 入参校验
        validation(reqVO);

        Volunteer volunteer;
        try {
            volunteer = volunteerMapper.selectByPrimaryKey(reqVO.getVolunteerId());
        }catch (Exception e){
            logger.error("查看志愿者更改组织的审核结果出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        // 如果志愿者状态不是更改组织中，直接返回
        if (volunteer.getStatus() != VolunteerStatusConstant.CHANGE_ORGANIZATION_CHECKING){
            if (volunteer.getStatus() == VolunteerStatusConstant.NO_PASS){
                resVO.setChangeResult(NO_PASS);
            }else if (volunteer.getStatus() == VolunteerStatusConstant.PASS){
                resVO.setChangeResult(PASS);
                resVO.setVolunteer(volunteer);
            }
            responseVO.setData(resVO);
            return responseVO;
        }

        // 如果是更改组织中，检测redis中是否还有对应的记录，没有则set
        String targetOrganization = redisUtil.getValue(RedisKeyUtil.getChangeOrganizationKey(reqVO.getVolunteerId()));
        if (!StringUtils.hasText(targetOrganization)){
            // 如果请求参数中也没有，则修改数据库志愿者状态为通过（即丢弃本次更改组织）
            if (reqVO.getTargetOrganization() == null){
                try {
                    volunteer.setStatus(VolunteerStatusConstant.PASS);
                    volunteerMapper.updateBySelected(volunteer);
                    resVO.setChangeResult(PASS);
                    resVO.setVolunteer(volunteer);
                    responseVO.setData(resVO);
                    return responseVO;
                }catch (Exception e){
                    logger.error("查看志愿者更改组织的审核结果出错", e);
                    throw new BaseException(ServiceEnum.DATABASE_ERROR);
                }
            }
            // 请求参数中有则放进redis
            redisUtil.setValue(RedisKeyUtil.getChangeOrganizationKey(reqVO.getVolunteerId()), reqVO.getTargetOrganization() + "");
        }
        // 执行到这里，则是更改组织中
        resVO.setChangeResult(WAIT_CHECK);
        responseVO.setData(resVO);
        return responseVO;
    }

    /**
     * * 入参校验
     * @param reqVo
     * @throws BaseException
     */
    private void validation(GetChangeOrgResultReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getVolunteerId() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }
}
