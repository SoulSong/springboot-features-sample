package com.shf.springboot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * Description:
 * Customized load properties files by spring.profiles.active.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/30 03:06
 */
@Configuration
@Slf4j
@PropertySource(value = {"classpath:application.properties",
        "classpath:custom-${spring.profiles.active}.properties"}, ignoreResourceNotFound = true)
public class PropertiesConfiguration {
    @Value("${profile-name:dev}")
    private String profile;

    @PostConstruct
    public void init() {
        log.info("current profile is {}, load from custom-{}.properties.", profile, profile);
    }
}
