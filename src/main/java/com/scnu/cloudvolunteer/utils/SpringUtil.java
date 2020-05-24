package com.scnu.cloudvolunteer.utils;

import com.scnu.cloudvolunteer.base.enums.BaseEnum;
import com.scnu.cloudvolunteer.base.exception.BeanNotFindException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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

    public static Object getBean(String name)throws BeanNotFindException {
        Object bean;
        try {
            bean = applicationContext.getBean(name);
            if (bean == null){
                throw new BeanNotFindException(BaseEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BeanNotFindException(BaseEnum.SVC_ERROR);
        }
        return bean;
    }

    public static <T> T getBean(Class<T> clazz)throws BeanNotFindException{
        T bean;
        try {
            bean = applicationContext.getBean(clazz);
            if (bean == null){
                throw new BeanNotFindException(BaseEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BeanNotFindException(BaseEnum.SVC_ERROR);
        }
        return bean;
    }

    public static <T> T getBean(String name, Class<T> clazz) throws BeanNotFindException{
        T bean;
        try {
            bean = applicationContext.getBean(name, clazz);
            if (bean == null){
                throw new BeanNotFindException(BaseEnum.SVC_ERROR);
            }
        }catch (BeansException e){
            throw new BeanNotFindException(BaseEnum.SVC_ERROR);
        }
        return bean;
    }

}
