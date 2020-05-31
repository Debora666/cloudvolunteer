package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.constant.DataUrlSchemeHeadersConstant;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.dto.FileContentDTO;

import java.util.Map;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description : 解析 Data URI scheme格式工具类
 * @modified By：
 */
public class DataUrlSchemeUtil {


    /**
     * 将Data URI scheme格式数据中取出文件数据并包装成FileContentDTO
     * @param source
     * @return 返回转换后的FileContentDTO
     * @throws BaseException
     */
    public static FileContentDTO decryptByString(String source) throws BaseException {
        boolean resultFlag = false;
        FileContentDTO fileContentDTO = new FileContentDTO();
        for(Map.Entry<String,String> entry: DataUrlSchemeHeadersConstant.toHashMap().entrySet()){
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

    /**
     * 将以Base64读取到的文件转化为Data URI scheme 格式
     * @param fileContentDTO 以Base64读取到的文件
     * @return
     */
    public static String encryptToDataUrlScheme(FileContentDTO fileContentDTO){
        return DataUrlSchemeHeadersConstant.toHashMap().get(fileContentDTO.getFileType()) + fileContentDTO.fileContent;
    }

//    /**
//     * 判断字符串是否为Data URI scheme格式
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
