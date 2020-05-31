package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.constant.Rfc2397HeadersConstant;
import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.dto.FileContentDTO;

import java.util.Map;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：解析RFC2397工具类
 * @modified By：
 */
public class Rfc2397Util {


    /**
     * 返回转换后的结果
     * @param source
     * @return
     * @throws BaseException
     */
    public static FileContentDTO decryptByString(String source) throws BaseException {
        boolean resultFlag = false;
        FileContentDTO fileContentDTO = new FileContentDTO();
        for(Map.Entry<String,String> entry: Rfc2397HeadersConstant.toHashMap().entrySet()){
            if(source.indexOf(entry.getValue()) == 0){
                fileContentDTO.fileContent = source.replace(entry.getValue(),"");
                fileContentDTO.fileType = entry.getKey();
                return fileContentDTO;
            }
        }
        //没有找到支持的文件类型或不符合RFC2397格式
        if(!resultFlag){throw new BaseException(UserEnum.REQUEST_PARAM_FORMAT_ERROE);};
        return fileContentDTO;
    }

    public static String encryptToRfc2397String(FileContentDTO fileContentDTO){
        return Rfc2397HeadersConstant.toHashMap().get(fileContentDTO.getFileType()) + fileContentDTO.fileContent;
    }

//    /**
//     * 判断字符串是否为RFC2397格式
//     * @param source
//     * @return 是则返true, 反之false
//     */
//    public static boolean isRfc2397String(String source){
//        boolean result = false;
//        for(Map.Entry<String,String> entry: Rfc2397HeadersConstant.toHashMap().entrySet()){
//            if(source.indexOf(entry.getValue()) != -1){
//                result = true;
//                break;
//            }
//        }
//        return result;
//    }



}
