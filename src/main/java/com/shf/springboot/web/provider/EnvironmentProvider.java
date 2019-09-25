package com.shf.springboot.web.provider;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/9/24 15:29
 */
public class EnvironmentProvider implements EnvironmentAware {

    private static Environment environment;

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }

    @Override
    public void setEnvironment(Environment environment) {
        EnvironmentProvider.environment = environment;
    }


}
