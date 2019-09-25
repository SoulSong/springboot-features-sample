package com.shf.springboot.web.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/9/24 17:59
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }
}
