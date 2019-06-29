package com.shf.springboot.configuration;

import com.shf.springboot.properties.SampleProperties;
import com.shf.springboot.properties.SamplePropertiesValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 22:18
 */
@Configuration
public class BeanConfiguration {

    /**
     * {@link Validated} annotation is used to validate {@link ConfigurationProperties} classes
     *
     * @return SampleProperties
     */
    @Bean
    @ConfigurationProperties(prefix = "sample")
    @Validated
    public SampleProperties sampleProperties() {
        return new SampleProperties();
    }

    /**
     * Register customized validator bean for {@link ConfigurationProperties}
     *
     * @return Validator
     */
    @Bean
    public Validator configurationPropertiesValidator() {
        return new SamplePropertiesValidator();
    }

}
