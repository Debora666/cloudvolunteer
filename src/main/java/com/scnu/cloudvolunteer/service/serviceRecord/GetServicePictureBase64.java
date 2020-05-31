package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.FilePathsConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dto.FileContentDTO;
import com.scnu.cloudvolunteer.dto.NocheckServiceDTO;
import com.scnu.cloudvolunteer.utils.*;
import com.scnu.cloudvolunteer.vo.serviceRecord.GetServicePictureBase64ReqVO;
import com.scnu.cloudvolunteer.vo.serviceRecord.GetServicePictureBase64ResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：查看某次服务信息的图片的服务
 * @modified By：
 */
@Service(SvcConstant.GET_SERVICE_PICTURE_BASE64)
public class GetServicePictureBase64 implements BaseService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ResponseVO service(String request) throws BaseException {
        //请求转VO
        GetServicePictureBase64ReqVO reqVO = JsonUtil.string2Obj(request, GetServicePictureBase64ReqVO.class);
        validation(reqVO);
        //初始化待审核服务信息map
        Map<String,NocheckServiceDTO> nocheckServiceDTOMap = new HashMap<String,NocheckServiceDTO>();
        try {
            //获取待审核服务信息map
            nocheckServiceDTOMap = redisUtil.getHash(RedisKeyUtil.getUncheckedServiceRecordMapKey());
        }catch(Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        //获取单条待审核服务信息
        NocheckServiceDTO nocheckServiceDTO = nocheckServiceDTOMap.get(reqVO.getServiceOrderId());
        if(nocheckServiceDTO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
        //文件数据列表
        ArrayList<String> filesData = new ArrayList<>();

        //读取文件，根据"/volunteerId/serviceRecordId/文件名"找到文件
        //这里将对读出的文件进行转换，转成Rfc2397格式
        for(int i=0;i<nocheckServiceDTO.getFileNameArrayList().size();i++){
            //文件读取后的DTO实体
            FileContentDTO fileContentDTO = new FileContentDTO();
            //生成文件路径
            Path filePath = Paths.get(FilePathsConstant.SERVICE_RECORD_FILES_SAVE_PATH,
                    nocheckServiceDTO.getVolunteerId().toString(),
                    nocheckServiceDTO.getServiceRecordId().toString(),
                    nocheckServiceDTO.getFileNameArrayList().get(i));
            try{
                //读取文件，并转换为Base64格式
                fileContentDTO = Base64Util.encryptToBase64(filePath);
            }catch (Exception e){
                throw new BaseException(AppEnum.APP_ERROR);
            }
            //将Base64格式的数据转换为Rfc2397格式并插入到文件数据列表
            filesData.add(DataUrlSchemeUtil.encryptToDataUrlScheme(fileContentDTO));
        }

        //响应VO
        GetServicePictureBase64ResVO getServicePictureBase64ResVO = new GetServicePictureBase64ResVO();
        getServicePictureBase64ResVO.setServiceRecordBase64(filesData);

        ResponseVO<GetServicePictureBase64ResVO> responseVO = new ResponseVO<>();
        responseVO.setData(getServicePictureBase64ResVO);
        return responseVO;
    }

    /**
     * 入参检查
     * @param reqVO
     * @throws BaseException
     */
    private void validation(GetServicePictureBase64ReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(!StringUtils.hasText(reqVO.getServiceOrderId())){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }

    }
}
