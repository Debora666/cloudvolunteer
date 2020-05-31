package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.dto.FileContentDTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author ：ben liang
 * @date ：2020/5/30
 * @description：Base64工具类
 * @modified By：
 */
public class Base64Util {

    /**
     * 把base64转化为文件并保存
     * @param base64   base64
     * @param filePath 目标文件路径
     * @return
     *  true-保存成功
     *  false-保存失败
     */
    public static Boolean decryptByBase64(String base64, Path filePath) throws Exception {

        if(!StringUtils.hasText(base64) || filePath == null) {
            return Boolean.FALSE;
        }
        try {
            //读取文件并进行转换
            Files.write(filePath,Base64.decodeBase64(base64.substring(base64.indexOf(",") + 1)), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw e;
        }
        return Boolean.TRUE;
    }

    /**
     * 以base64形式读取文件
     * @param filePath 源文件路径
     * @return FileContentDTO 读取后的结果
     */
    public static FileContentDTO encryptToBase64(Path filePath) throws Exception {
        if (!(filePath == null)) {
            try {
                byte[] bytes = Files.readAllBytes(filePath);
                //读取文件内容
                String fileContent = Base64.encodeBase64String(bytes);
                //初始化FileContentDTO
                FileContentDTO fileContentDTO = new FileContentDTO();
                //将文件内容注入
                fileContentDTO.setFileContent(fileContent);
                //获取文件后缀
                String filePathString = filePath.toString();
                String[] splitArray  = filePathString.split("\\.");
                String suffix = splitArray[splitArray.length - 1];
                //注入文件格式
                fileContentDTO.setFileType(suffix);
            } catch (IOException e) {
                throw e;
            }
        }
        return null;
    }
}