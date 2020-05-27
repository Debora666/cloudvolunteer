package com.scnu.cloudvolunteer.base.service;

import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:22:21
 * @description：
 *  文件服务接口
 */
public interface BaseFileService {

     /**
      * 处理请求方法
      * 所有文件上传请求通过该方法完成处理
      * @param request
      * @return
      * @throws BaseException
      */
     @Transactional(rollbackFor = Exception.class)
     ResponseVO service(String request, MultipartFile file) throws BaseException;
}
