package com.scnu.cloudvolunteer.service.service;

import com.scnu.cloudvolunteer.base.constant.ServiceStatusConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.OrderServiceMapper;
import com.scnu.cloudvolunteer.dao.pojo.OrderService;
import com.scnu.cloudvolunteer.vo.service.GetNoMatchServiceResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zzheng
 * @date ：2020/5/28
 * 获取所有未匹配的服务订单
 */
@Service(SvcConstant.GET_NO_MATCH_SERVICE)
public class GetNoMatchService implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(GetNoMatchService.class);

    @Autowired
    private OrderServiceMapper orderServiceMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<GetNoMatchServiceResVO> responseVO = new ResponseVO<>();
        GetNoMatchServiceResVO resVO = new GetNoMatchServiceResVO();
        List<OrderService> noMatchServices;
        try {
            noMatchServices = orderServiceMapper.selectByServiceStatus(ServiceStatusConstant.WAIT_MATCH);
        }catch (Exception e){
            logger.error("获取未匹配服务出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        resVO.setServices(noMatchServices);
        responseVO.setData(resVO);
        return responseVO;
    }

}
