package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import org.springframework.stereotype.Service;

/**
 * @author ：ben liang
 * @date ：2020/5/30
 * @description：上传服务信息
 * @modified By：
 */
@Service(SvcConstant.UPLOAD_SERVICE_RECORD)
public class UploadServiceRecord implements BaseService {

    @Override
    public ResponseVO service(String request) throws BaseException {
        return null;
    }
}
