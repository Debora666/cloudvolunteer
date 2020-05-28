package com.scnu.cloudvolunteer.service.service;

import com.scnu.cloudvolunteer.base.constant.ServiceStatusConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.OrderServiceMapper;
import com.scnu.cloudvolunteer.dao.ServiceRecordMapper;
import com.scnu.cloudvolunteer.dao.pojo.OrderService;
import com.scnu.cloudvolunteer.dao.pojo.ServiceRecord;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.service.HandMatchReqVO;
import com.scnu.cloudvolunteer.vo.service.HandMatchResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 人工匹配
 */
@Service(SvcConstant.HAND_MATCH)
public class HandMatchService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(HandMatchService.class);

    @Autowired
    private OrderServiceMapper orderServiceMapper;
    @Autowired
    private ServiceRecordMapper serviceRecordMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<HandMatchResVO> responseVO = new ResponseVO<>();
        HandMatchReqVO reqVO = JsonUtil.string2Obj(request, HandMatchReqVO.class);
        validation(reqVO);
        try {
            int size = reqVO.getVolunteerIds().size();
            List<ServiceRecord> serviceRecords = new ArrayList<>(size);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            for (int i = 0; i < size; i++){
                ServiceRecord serviceRecord = new ServiceRecord();
                serviceRecord.setOrderServiceId(reqVO.getOrderServiceId());
                serviceRecord.setVolunteerId(reqVO.getVolunteerIds().get(i));
                serviceRecord.setUserId(reqVO.getUserId());
                serviceRecord.setServiceStatus(ServiceStatusConstant.MATCH);
                serviceRecord.setWorkTime(0);
                serviceRecord.setUpdateDate(timestamp);
                serviceRecord.setCreateDate(timestamp);
                serviceRecords.add(serviceRecord);
            }
            // 批量插入
            serviceRecordMapper.insertBatch(serviceRecords);
            // 更新订单表的服务状态为匹配
            OrderService orderService = new OrderService();
            orderService.setOrderServiceId(reqVO.getOrderServiceId());
            orderService.setServiceStatus(ServiceStatusConstant.MATCH);
            orderServiceMapper.updateBySelected(orderService);
        }catch (Exception e){
            logger.error("人工匹配出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        return responseVO;
    }

    /**
     * * 入参校验
     * @param reqVo
     * @throws BaseException
     */
    private void validation(HandMatchReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getAdminId() == null || reqVo.getUserId() == null
                || reqVo.getVolunteerIds() == null || reqVo.getOrderServiceId() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getVolunteerIds().size() == 0){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }

    }

}
