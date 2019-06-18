package com.shf.springboot.configuration;

import com.shf.springboot.conversion.IdConversionService;
import com.shf.springboot.conversion.IdConverter;
import com.shf.springboot.properties.SampleProperties;
import com.shf.springboot.properties.SamplePropertiesValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
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
     * Register custom conversion service
     *
     * @return {@link ConversionService}
     */
    @Bean
    public ConversionService idConversionService() {
        return new IdConversionService();
    }

    /**
     * {@link IdConverter} bean depends on {@link IdConversionService}
     *
     * @param idConversionService idConversionService
     * @return IdConverter
     */
    @Bean
    public IdConverter idConverter(ConversionService idConversionService) {
        return new IdConverter(idConversionService);
    }

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
