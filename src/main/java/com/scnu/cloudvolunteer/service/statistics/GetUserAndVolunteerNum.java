package com.scnu.cloudvolunteer.service.statistics;

import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.UserMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.vo.statistics.GetUserAndVolunteerNumResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 查看平台所有用户和志愿者的统计数
 */
@Service(SvcConstant.GET_USER_STATISTICS)
public class GetUserAndVolunteerNum implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(GetUserAndVolunteerNum.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        ResponseVO<GetUserAndVolunteerNumResVO> responseVO = new ResponseVO<>();
        GetUserAndVolunteerNumResVO resVO = new GetUserAndVolunteerNumResVO();
        try {
            int userNum = userMapper.selectCount();
            int volunteerNum = volunteerMapper.selectVolunteerCount();
            resVO.setUserNum(userNum);
            resVO.setVolunteerNum(volunteerNum);
        }catch (Exception e){
            logger.error("查看平台所有用户和志愿者的统计数出错", e);
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        responseVO.setData(resVO);
        return responseVO;
    }
}
