package com.shf.springboot.configuration;

import com.shf.springboot.conversion.IdConverter;
import com.shf.springboot.i18n.MessageAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Description:
 * - Do not add {@link @EnableWebMvc} annotation, This will make the jackson setting in application.properties invalid.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 22:53
 */

@ConditionalOnWebApplication
@Configuration
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class, MessageAutoConfiguration.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class WebMvcConfiguration {

    /**
     * configuration for formatter and converter
     */
    @Configuration
    public static class WebConverterConfiguration implements WebMvcConfigurer {
        @Autowired
        private IdConverter idConverter;

        /**
         * Add custom converter into {@link FormatterRegistry} for mvc.
         *
         * @param registry {@link FormatterRegistry}
         */
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(idConverter);
        }

    }

    /**
     * Configuration for i18n
     */
    @Configuration
    public static class WebI18nConfiguration implements WebMvcConfigurer {
        @Autowired
        private MessageSource messageSource;

        /**
         * Set default language is zh_CN
         *
         * @return {@link LocaleResolver}
         */
        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
            sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
            return sessionLocaleResolver;
        }

        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
            localeChangeInterceptor.setParamName("lang");
            return localeChangeInterceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }

        /**
         * Set messageSource for validation-message i18n
         *
         * @return {@link LocalValidatorFactoryBean}
         */
        @Override
        public Validator getValidator() {
            LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
            validator.setValidationMessageSource(messageSource);
            return validator;
        }
    }

    @Configuration
    public static class WebGeneralConfiguration implements WebMvcConfigurer {
        /**
         * Add a simple light endpoint for health-check.
         *
         * @param registry {@link ViewControllerRegistry}
         */
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addStatusController("/ping", HttpStatus.OK);
        }
    }

    /**
     * Configuration for i18n
     */
    @Configuration
    public static class WebErrorConfiguration implements WebMvcConfigurer {
        private ServerProperties serverProperties;
        private List<ErrorViewResolver> errorViewResolvers;

        @Autowired(required = false)
        public void setServerProperties(ServerProperties serverProperties) {
            this.serverProperties = serverProperties;
        }

        @Autowired(required = false)
        public void setErrorViewResolvers(List<ErrorViewResolver> errorViewResolvers) {
            this.errorViewResolvers = errorViewResolvers;
        }

//        @Bean
//        @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
//        public CustomizedErrorController basicErrorController(ErrorAttributes errorAttributes) {
//            return new CustomizedErrorController(errorAttributes,
//                    this.serverProperties == null ? new ErrorProperties() : this.serverProperties.getError(),
//                    this.errorViewResolvers);
//        }
    }
}

