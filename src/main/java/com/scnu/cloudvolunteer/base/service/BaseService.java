package com.scnu.cloudvolunteer.base.service;

import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:23:46
 * @description：
 *  基础服务接口
 */
public interface BaseService {

     /**
      * 处理请求方法
      * 所有请求通过该方法完成处理
      * @param request
      * @return
      * @throws BaseException
      */
     @Transactional(rollbackFor = Exception.class)
     ResponseVO service(String request) throws BaseException;
}
