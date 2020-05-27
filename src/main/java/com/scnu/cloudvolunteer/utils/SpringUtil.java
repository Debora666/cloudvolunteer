package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：zzheng
 * @date ：2020/05/24 19:31:06
 * @description：
 *  spring工具类，获取bean
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 根据bean名字找到bean
     * @param name
     * @return
     * @throws BaseException
     */
    public static Object getBean(String name)throws BaseException {
        Object bean;
        try {
            bean = applicationContext.getBean(name);
            if (bean == null){
                throw new BaseException(UserEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BaseException(UserEnum.SVC_ERROR);
        }
        return bean;
    }

    /**
     * 根据bean的class找到bean
     * @param clazz
     * @param <T>
     * @return
     * @throws BaseException
     */
    public static <T> T getBean(Class<T> clazz)throws BaseException{
        T bean;
        try {
            bean = applicationContext.getBean(clazz);
            if (bean == null){
                throw new BaseException(UserEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BaseException(UserEnum.SVC_ERROR);
        }
        return bean;
    }

    /**
     * 根据bean的名字和class找到bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     * @throws BaseException
     */
    public static <T> T getBean(String name, Class<T> clazz) throws BaseException{
        T bean;
        try {
            bean = applicationContext.getBean(name, clazz);
            if (bean == null){
                throw new BaseException(UserEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BaseException(UserEnum.SVC_ERROR);
        }
        return bean;
    }


    /**
     * 两个pojo相同的属性  赋值
     * @param src   源pojo
     * @param target  要赋值的pojo
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 返回pojo里不为空的属性数组
     * @param source
     * @return
     */
    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
