package com.scnu.cloudvolunteer.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author zzheng
 * @date 2020/05/24 19:34:25
 * @description：
 * JSON工具类
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * JavaBean转为json格式
     * @param obj JavaBean
     * @param <T>
     * @return
     */
    public static <T> String object2Json(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json格式字符串转换为JavaBean
     * @param str json字符串
     * @param clazz JavaBean
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (str.length() == 0 || clazz == null) {
            return null;
        }
        try {
            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class... elementClasses) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return (T)mapper.readValue(str, javaType);
        } catch (IOException e) {
            System.out.println("Parse String to Object error");
            e.printStackTrace();
            return null;
        }
    }
}
