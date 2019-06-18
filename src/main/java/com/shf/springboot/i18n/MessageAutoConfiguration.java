package com.shf.springboot.i18n;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/17 23:27
 */
@Configuration
@ConditionalOnClass({MessageSourceAware.class})
public class MessageAutoConfiguration {
    @Bean
    public MessageSourceAware messageSourceAware() {
        return MessageHelper::setMessageSource;
    }
}