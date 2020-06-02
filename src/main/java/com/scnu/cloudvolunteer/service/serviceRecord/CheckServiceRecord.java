package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SubscribeMessageTamplateIdConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.ServiceRecordMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.ServiceRecord;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.dto.NocheckServiceDTO;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.RedisKeyUtil;
import com.scnu.cloudvolunteer.utils.RedisUtil;
import com.scnu.cloudvolunteer.utils.WechatHttp;
import com.scnu.cloudvolunteer.vo.serviceRecord.CheckServiceRecordReqVO;
import com.scnu.cloudvolunteer.vo.subscribeMessageVO.SendSubscribeMessagerReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ben liang
 * @date ：2020/6/2
 * @description：审核服务消息的服务
 * @modified By：
 */
@Service(SvcConstant.CHECK_SERVICE_RECORD)
public class CheckServiceRecord implements BaseService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    VolunteerMapper volunteerMapper;

    @Autowired
    ServiceRecordMapper serviceRecordMapper;

    Logger logger = LoggerFactory.getLogger(CheckServiceRecord.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO service(String request) throws BaseException {
        CheckServiceRecordReqVO reqVO = JsonUtil.string2Obj(request, CheckServiceRecordReqVO.class);
        validation(reqVO);
        Map<String,Object> nocheckServiceRecordMap = redisUtil.getHash(RedisKeyUtil.getUncheckedServiceRecordMapKey());
        NocheckServiceDTO nocheckServiceDTO = (NocheckServiceDTO) nocheckServiceRecordMap.get(reqVO.getServiceOrderId());
        if(nocheckServiceDTO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        if(admin.getRole() == RoleConstant.ADMIN2 && admin.getOrganization() != nocheckServiceDTO.getOrganization()){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }

        //如果通过
        if(reqVO.getResult() == 1){
            checkSuccess(reqVO,nocheckServiceDTO);
            logger.info("管理员[{}]审核服务[{}]为成功",reqVO.getAdminId(),reqVO.getServiceOrderId());
        }else{
            checkFail(reqVO,nocheckServiceDTO);
            logger.info("管理员[{}]审核服务[{}]为失败",reqVO.getAdminId(),reqVO.getServiceOrderId());
        }

        ResponseVO<Integer> responseVO = new ResponseVO<>();
        return responseVO;
    }



    /**
     * 没通过审核的操作
     * @param reqVO
     * @param nocheckServiceDTO
     * @throws BaseException
     */
    private void checkFail(CheckServiceRecordReqVO reqVO, NocheckServiceDTO nocheckServiceDTO)throws BaseException{
        //获取volunteer和ServiceRecord用于发送订阅消息
        Volunteer volunteer;
        try{
            volunteer = volunteerMapper.selectByPrimaryKey(nocheckServiceDTO.getVolunteerId());
        }catch (Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        if(volunteer == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        String reason = reqVO.getReason() == null? "" : reqVO.getReason();

        //发送订阅消息前准备工作
        SendSubscribeMessagerReqVO sendSubscribeMessagerReqVO = new SendSubscribeMessagerReqVO();
        sendSubscribeMessagerReqVO.setAccessToken(redisUtil.getValue("accessToken"));
        sendSubscribeMessagerReqVO.setTouser(volunteer.getOpenid());
        sendSubscribeMessagerReqVO.setTemplateId(SubscribeMessageTamplateIdConstant.CHECK_FAIL);
        //TODO 确定订阅消息模板后更新
        sendSubscribeMessagerReqVO.setData(new HashMap<String,Map<String,String>>(){{
            put("key1",new HashMap<String,String>(){{
                put("value", "hh");
            }});
        }});
        //发送订阅消息
        WechatHttp.sendTemplateMsg(redisUtil.getValue("accessToken"),sendSubscribeMessagerReqVO);
        //完成发送后，在Redis中删除数据
        try{
            redisUtil.del(reqVO.getServiceOrderId());
        }catch (Exception e){
            throw  new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }

    /**
     * 通过审核的操作
     * @param reqVO
     * @param nocheckServiceDTO
     * @throws BaseException
     */
    private void checkSuccess(CheckServiceRecordReqVO reqVO, NocheckServiceDTO nocheckServiceDTO)throws BaseException{
        //获取服务时间:
        //  若workTime参数为空，服务时间由志愿者提交的服务时间进行记录
        //  若workTime参数非空，以提交请求中的服务时间为新的服务时间进行记录
        int realWorkTime = reqVO.getWorkTime() == null ? nocheckServiceDTO.getWorkTime() : reqVO.getWorkTime();
        //获取volunteer和ServiceRecord用于更新数据
        Volunteer volunteer;
        ServiceRecord serviceRecord;
        try{
            volunteer = volunteerMapper.selectByPrimaryKey(nocheckServiceDTO.getVolunteerId());
            serviceRecord = serviceRecordMapper.selectByPrimaryKey(nocheckServiceDTO.getServiceRecordId());
        }catch (Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        //没有查询到记录
        if(volunteer == null || serviceRecord == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }

        serviceRecord.setWorkTime(serviceRecord.getWorkTime() + realWorkTime);
        volunteer.setTotalWorkTime(volunteer.getServiceTime() + realWorkTime);

        //发送订阅消息前准备工作
        SendSubscribeMessagerReqVO sendSubscribeMessagerReqVO = new SendSubscribeMessagerReqVO();
        sendSubscribeMessagerReqVO.setAccessToken(redisUtil.getValue("accessToken"));
        sendSubscribeMessagerReqVO.setTouser(volunteer.getOpenid());
        sendSubscribeMessagerReqVO.setTemplateId(SubscribeMessageTamplateIdConstant.CHECK_SUCCESS);
        //TODO 确定订阅消息模板后更新
        sendSubscribeMessagerReqVO.setData(new HashMap<String,Map<String,String>>(){{
            put("key1",new HashMap<String,String>(){{
                put("value", "hh");
            }});
        }});
        //发送订阅消息
        WechatHttp.sendTemplateMsg(redisUtil.getValue("accessToken"),sendSubscribeMessagerReqVO);

        //完成发送后，更新数据，并在Redis中删除数据
        try{
            volunteerMapper.updateBySelected(volunteer);
            serviceRecordMapper.updateBySelected(serviceRecord);
            redisUtil.del(reqVO.getServiceOrderId());
        }catch (Exception e){
            throw  new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }

    /**
     * 入参验证
     * @param reqVO
     * @throws BaseException
     */
    private void validation(CheckServiceRecordReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId() == null
                || reqVO.getVolunteerId() == null
                || reqVO.getResult() == null
                || reqVO.getServiceOrderId() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        //通过时，要带上服务时长
        if(reqVO.getResult() == 1 && reqVO.getWorkTime() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        //不通过时，需要带上不通过原因
//        if(reqVO.getResult() == 0 && reqVO.getReason() == null){
//            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
//        }
    }
}
