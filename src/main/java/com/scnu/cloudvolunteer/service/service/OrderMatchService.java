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
import com.scnu.cloudvolunteer.dto.MatchResultDTO;
import com.scnu.cloudvolunteer.dto.ServiceMatchDTO;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.service.OrderMatchReqVO;
import com.scnu.cloudvolunteer.vo.service.OrderMatchResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author ：zzheng
 * @date ：2020/5/27
 * 用户下单，系统匹配
 */
@Service(SvcConstant.ORDER_SERVICE_AND_MATCH)
public class OrderMatchService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(OrderMatchService.class);

    // 志愿者最多可服务对象数目
    private final int MAX_SERVICE_NUM = 3;
    // 数据库志愿者查询限制查询条数
    private final int LIMIT_NUM = 50;
    // 心理学院id
    private final int PSYCHOLOGY_COLLEGE = 2011;
    // 心理学科id
    private final int PSYCHOLOGICAL_COUNSELING = 301;

    @Autowired
    private ServiceRecordMapper serviceRecordMapper;
    @Autowired
    private OrderServiceMapper orderServiceMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<OrderMatchResVO> responseVO = new ResponseVO<>();
        OrderMatchResVO resVO = new OrderMatchResVO();
        int matchStatus = ServiceStatusConstant.WAIT_MATCH;
        // 将json字符串请求参数转换为实体类
        OrderMatchReqVO reqVO = JsonUtil.string2Obj(request, OrderMatchReqVO.class);
        // 入参校验
        validation(reqVO);

        // 找出一对一完全匹配的志愿者
        ServiceMatchDTO serviceMatchDTO = new ServiceMatchDTO();
        serviceMatchDTO.setSection(reqVO.getSection());
        serviceMatchDTO.setSubject(reqVO.getSubject());
        serviceMatchDTO.setServiceTime(reqVO.getServiceTime());
        serviceMatchDTO.setMaxServiceNum(MAX_SERVICE_NUM);
        serviceMatchDTO.setLimitNum(LIMIT_NUM);
        List<MatchResultDTO> matchVolunteers;
        try {
            matchVolunteers = serviceRecordMapper.selectMatchVolunteer(serviceMatchDTO);
        }catch (Exception e){
            logger.error("系统匹配出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        // 如果有完全匹配且服务对象少于最多服务对象要求的，进行筛选匹配
        MatchResultDTO matchResultDTO = null;
        if (matchVolunteers != null && matchVolunteers.size() > 0){
            matchStatus = ServiceStatusConstant.MATCH;
            matchResultDTO = matchService(reqVO, matchVolunteers);
        }

        //进行数据库插入，记录匹配结果
        insertService(reqVO, matchResultDTO, matchStatus);
        // 构造响应参数
        resVO.setServiceStatus(matchStatus);
        responseVO.setData(resVO);
        return responseVO;
    }


    /**
     * * 入参校验
     * @param reqVo
     * @throws BaseException
     */
    private void validation(OrderMatchReqVO reqVo)throws BaseException {
        if (reqVo == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getSubject() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getSection() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVo.getServiceTime() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }

    /**
     * 进行服务匹配
     * @param reqVO
     * @param matchVolunteers
     * @return
     * @throws BaseException
     */
    private MatchResultDTO matchService(OrderMatchReqVO reqVO, List<MatchResultDTO> matchVolunteers)throws BaseException{
        // 如果是心里辅导，优先匹配心理学院的志愿者
        if (reqVO.getSubject() == PSYCHOLOGICAL_COUNSELING){
            // 去掉不是心理学院的
            List<MatchResultDTO> l = matchVolunteers.stream()
                    .filter(n -> n.getOrganization() == PSYCHOLOGY_COLLEGE)
                    .collect(Collectors.toList());
            // 如果存在心理学院的志愿者，从心理学院中筛选
            if (l.size() != 0){
                matchVolunteers = l;
            }
        }
        // 如果只有一位志愿者匹配，直接返回
        if (matchVolunteers.size() == 1){
            return matchVolunteers.get(0);
        }
        // 获取最少服务对象的的一批志愿者，
        int minServiceNum = matchVolunteers.get(0).getNum();
        matchVolunteers = matchVolunteers.stream().filter(n -> n.getNum() == minServiceNum)
                .collect(Collectors.toList());
        // 随机从最少服务对象中选取一位进行匹配
        Random random = new Random();
        return matchVolunteers.get(random.nextInt(matchVolunteers.size()));
    }

    /**
     * 插入数据库
     * @param reqVO
     * @param matchResultDTO
     * @param matchStatus
     * @throws BaseException
     */
    private void insertService(OrderMatchReqVO reqVO, MatchResultDTO matchResultDTO, int matchStatus)throws BaseException{
        try {
            // 插入order_service
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            OrderService orderService = new OrderService();
            orderService.setUserId(reqVO.getUserId());
            orderService.setSubject(reqVO.getSubject());
            orderService.setSection(reqVO.getSection());
            orderService.setServiceTime(reqVO.getServiceTime());
            if (reqVO.getRemark() != null) {
                orderService.setRemark(reqVO.getRemark());
            }
            orderService.setServiceStatus(matchStatus);
            orderService.setUpdateDate(timestamp);
            orderService.setCreateDate(timestamp);
            orderServiceMapper.insert(orderService);
            // 如果匹配不成功，结束
            if (matchStatus == ServiceStatusConstant.WAIT_MATCH){
                return;
            }
            // 匹配成功，则插入service
            timestamp = new Timestamp(System.currentTimeMillis());
            ServiceRecord serviceRecord = new ServiceRecord();
            serviceRecord.setOrderServiceId(orderService.getOrderServiceId());
            serviceRecord.setVolunteerId(matchResultDTO.getVolunteerId());
            serviceRecord.setUserId(reqVO.getUserId());
            serviceRecord.setServiceStatus(matchStatus);
            serviceRecord.setWorkTime(0);
            serviceRecord.setUpdateDate(timestamp);
            serviceRecord.setCreateDate(timestamp);
            serviceRecordMapper.insert(serviceRecord);
        }catch (Exception e){
            logger.error("系统匹配出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
    }
}
