package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.exception.BaseException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author ：ben liang
 * @date ：2020/5/30
 * @description：Base64工具类
 * @modified By：
 */
public class Base64Util {

    /**
     * 把base64转化为文件.
     * @param base64   base64
     * @param filePath 目标文件路径
     * @return boolean isTrue
     */
    public static Boolean decryptByBase64(String base64, Path filePath) throws Exception {

        if(!StringUtils.hasText(base64) || filePath == null) {
            return Boolean.FALSE;
        }
        try {
            Files.write(filePath,Base64.decodeBase64(base64.substring(base64.indexOf(",") + 1)), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw e;
        }
        return Boolean.TRUE;
    }

    /**
     * 把文件转化为base64.
     * @param filePath 源文件路径
     * @return String 转化后的base64
     */
    public static String encryptToBase64(Path filePath) throws Exception {
        if (!(filePath == null)) {
            try {
                byte[] bytes = Files.readAllBytes(filePath);
                return Base64.encodeBase64String(bytes);
            } catch (IOException e) {
                throw e;
            }
        }
        return null;
    }
}
