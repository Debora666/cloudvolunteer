package com.scnu.cloudvolunteer.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scnu.cloudvolunteer.base.enums.AppEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zzheng
 * @date 2020/05/24 19:34:25
 * @description：
 * JSON工具类
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * JavaBean转为json格式
     * @param obj JavaBean
     * @param <T>
     * @return
     */
    public static <T> String object2Json(T obj) throws BaseException {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("object2Json出错", e);
            throw new BaseException(AppEnum.APP_ERROR);
        }
    }

    /**
     * json格式字符串转换为JavaBean
     * @param str json字符串
     * @param clazz JavaBean
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<T> clazz)  throws BaseException {
        if (str.length() == 0 || clazz == null) {
            return null;
        }
        try {
            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            logger.error("object2Json出错", e);
            throw new BaseException(AppEnum.APP_ERROR);
        }
    }

    /**
     * json格式字符串转换为  集合JavaBean
     * @param str json字符串
     * @param collectionClass 集合类型
     * @param elementClasses  集合元素类型（JavaBean）
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class... elementClasses)
            throws BaseException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return (T)mapper.readValue(str, javaType);
        } catch (IOException e) {
            logger.error("object2Json出错", e);
            throw new BaseException(AppEnum.APP_ERROR);
        }
    }
}
