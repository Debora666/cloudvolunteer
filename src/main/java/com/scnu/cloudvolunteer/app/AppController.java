package com.scnu.cloudvolunteer.app;

import com.scnu.cloudvolunteer.base.enums.BaseEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:04:01
 * @description：
 *  全局入口
 */
@RestController
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @PostMapping("/cloudvolunteer")
    public String app(@RequestBody String request, @RequestHeader String svc){
        logger.info("接收前端请求, svc:[{}], request:[{}]", svc, request);
        String response;
        ResponseVO responseVo;
        try{
            // 校验svc
            validation(svc);
            // 根据svc获取对应的服务
            BaseService bean =  (BaseService) SpringUtil.getBean(svc);
            // 处理请求
            responseVo = bean.service(request);
            // 返回响应参数
            responseVo.setCode(BaseEnum.ok.getCode());
            responseVo.setMessage(BaseEnum.ok.getMessage());
            // 序列化成json字符串
            response = JsonUtil.object2Json(responseVo);
        } catch (BaseException e){
            logger.error("请求失败，code[{}], [{}]", e.getCode(), e.getMessage());
            responseVo = new ResponseVO();
            responseVo.setCode(e.getCode());
            responseVo.setMessage(e.getMessage());
            return JsonUtil.object2Json(responseVo);
        }catch (Exception e){
            logger.error("请求未知异常", e);
            responseVo = new ResponseVO();
            responseVo.setCode(BaseEnum.ERROR.getCode());
            responseVo.setMessage(BaseEnum.ERROR.getMessage());
            return JsonUtil.object2Json(responseVo);
        }
        logger.info("返回响应参数, response[{}]", response);
        return response;
    }

//    @PostMapping("/cloudvolunteer/file")
//    public String file(AutoUploadReqVo request, MultipartFile file, @RequestHeader String svc){
//        logger.info("接收前端请求, svc:[{}], request:[{}]，fileName:[{}]", svc, request, file.getOriginalFilename());
//        String response;
//        ResponseVo responseVo;
//        try{
//            // 校验svc
//            validationFile(svc);
//            // 获取对应的服务
//            BaseFileService bean =  (BaseFileService) SpringUtil.getBean(svc);
//            // 处理请求
//            String reqVo = JsonUtil.object2Json(request);
//            responseVo = bean.service(reqVo, file);
//            // 返回响应参数
//            responseVo.setCode(BaseEnum.ok.getCode());
//            responseVo.setMessage(BaseEnum.ok.getMessage());
//            response = JsonUtil.object2Json(responseVo);
//        } catch (BaseException e){
//            logger.error("请求失败，code[{}], [{}]", e.getCode(), e.getMessage());
//            responseVo = new ResponseVo();
//            responseVo.setCode(e.getCode());
//            responseVo.setMessage(e.getMessage());
//            return JsonUtil.object2Json(responseVo);
//        }catch (Exception e){
//            logger.error("请求未知异常", e);
//            responseVo = new ResponseVo();
//            responseVo.setCode(BaseEnum.ERROR.getCode());
//            responseVo.setMessage(BaseEnum.ERROR.getMessage());
//            return JsonUtil.object2Json(responseVo);
//        }
//        logger.info("返回响应参数, response[{}]", response);
//        return response;
//    }

    /**
     * svc检验
     * @param svc
     * @throws BaseException
     */
    private void validation(String svc) throws BaseException{
        if (StringUtils.isEmpty(svc)){
            throw new BaseException(UserEnum.SVC_NULL);
        }
        if (svc.length() != 4){
            throw new BaseException(UserEnum.SVC_ERROR);
        }
    }

    /**
     * 文件上传验证svc
     * @param svc
     */
//    private void validationFile(String svc) throws BaseException{
//        if (SvcConstant.WORD_UPLOAD.equals(svc)){
//            return;
//        }else if (SvcConstant.EXCEL_UPLOAD.equals(svc)){
//            return;
//        }
//        validation(svc);
//        throw new BaseException(BaseEnum.SVC_VOID);
//    }
}
