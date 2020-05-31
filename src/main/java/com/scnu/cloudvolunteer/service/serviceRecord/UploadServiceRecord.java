package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.FilePathsConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.ServiceRecordMapper;
import com.scnu.cloudvolunteer.dao.pojo.ServiceRecord;
import com.scnu.cloudvolunteer.dto.FileContentDTO;
import com.scnu.cloudvolunteer.dto.SingleServiceDTO;
import com.scnu.cloudvolunteer.utils.*;
import com.scnu.cloudvolunteer.vo.serviceRecord.UpLoadServiceRecordReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author ：ben liang
 * @date ：2020/5/30
 * @description：上传服务信息
 * @modified By：
 */
@Service(SvcConstant.UPLOAD_SERVICE_RECORD)
public class UploadServiceRecord implements BaseService {
    @Autowired
    private ServiceRecordMapper serviceRecordMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO service(String request) throws BaseException {
        //获取创建时间
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        UpLoadServiceRecordReqVO reqVO = JsonUtil.string2Obj(request, UpLoadServiceRecordReqVO.class);
        //验证请求
        validation(reqVO);
        //文件路径
        ArrayList<String> fileNamesArrayList = new ArrayList<>();
        for(int i = 0; i < reqVO.getFiles().size(); i++) {
            try {
                FileContentDTO fileContentDTO = Rfc2397Util.decryptByString(reqVO.getFiles().get(i));
                String fileName = generateFileName(fileContentDTO.getFileType());
                Path filePath = Paths.get(FilePathsConstant.SERVICE_RECORD_FILES_SAVE_PATH,
                        reqVO.getVolunteerId().toString(),
                        reqVO.getServiceRecordId().toString(),
                        fileName);
                Base64Util.decryptByBase64(reqVO.getFiles().get(0),filePath);
                fileNamesArrayList.add(fileName);
            } catch (Exception e) {
                throw new BaseException(AppEnum.APP_ERROR);
            }
        }

        ServiceRecord serviceRecord = serviceRecordMapper.selectByPrimaryKey(reqVO.getServiceRecordId());

        //开始写入单次服务信息
        SingleServiceDTO singleServiceDTO = new SingleServiceDTO();
        singleServiceDTO.setCreateTime(currTime);
        singleServiceDTO.setVolunteerId(reqVO.getVolunteerId());
        singleServiceDTO.setServiceRecordId(reqVO.getServiceRecordId());
        singleServiceDTO.setFileNameArrayList(fileNamesArrayList);
        singleServiceDTO.setWorkTime(reqVO.getWorkTime());
        singleServiceDTO.setOrderServiceId(serviceRecord.getOrderServiceId());
        //获取为未审核的服务记录Map
        Map<String, Object> serviceRecordMap = redisUtil.getHash(RedisKeyUtil.getUncheckedServiceRecordMapKey());
        String serviceOrderId = singleServiceDTO.getServiceRecordId() + "-" + currTime;
        serviceRecordMap.put(serviceOrderId,singleServiceDTO);
        //写入Redis
        try{
            redisUtil.setHash(RedisKeyUtil.getUncheckedServiceRecordMapKey(),serviceRecordMap);
        }catch(Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }


        ResponseVO<Integer> responseVO = new ResponseVO<>();
        return responseVO;
    }

    private String generateFileName(){
        Long timeStamp = System.currentTimeMillis() + 1;
        String result = String.valueOf(timeStamp);
        return result;
    }

    private String generateFileName(String suffix){
        String result = generateFileName() + "." + suffix;
        return result;
    }


    private void validation(UpLoadServiceRecordReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getVolunteerId() == null
                ||reqVO.getServiceRecordId() == null
                ||reqVO.getWorkTime() == null
                ||reqVO.getFiles() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getFiles().size()<1
                || reqVO.getFiles().size() > 9
                || reqVO.getWorkTime() < 0){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        ServiceRecord serviceRecord = serviceRecordMapper.selectByPrimaryKey(reqVO.getServiceRecordId());
        if(serviceRecord == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_ERROR);
        }
        if(serviceRecord.getVolunteerId() != reqVO.getVolunteerId()){
            throw new BaseException(UserEnum.REQUEST_PARAM_ERROR);
        }
    }
}
