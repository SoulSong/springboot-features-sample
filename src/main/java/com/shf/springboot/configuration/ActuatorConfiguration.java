package com.shf.springboot.configuration;

import com.shf.springboot.actuator.CustomizedWebMvcTagsProvider;
import com.shf.springboot.filter.CommonRequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Description:
 * Config for actuator and logging.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/30 02:26
 */
@Configuration
public class ActuatorConfiguration {

    /**
     * https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/#production-ready-metrics-meter
     *
     * @return CustomerWebMvcTagsProvider
     */
    @Bean
    public CustomizedWebMvcTagsProvider webMvcTagsProvider() {
        return new CustomizedWebMvcTagsProvider();
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        CommonRequestLoggingFilter filter = new CommonRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        FilterRegistrationBean<CommonRequestLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>(filter);
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/actuator/*"));
        return filterRegistrationBean;
    }
}
