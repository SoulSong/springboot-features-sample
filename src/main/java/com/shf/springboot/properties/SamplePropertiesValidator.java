package com.shf.springboot.properties;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Description:
 * Custom validator for {@link org.springframework.boot.context.properties.ConfigurationProperties}
 *
 * @Author: songhaifeng
 * @Date: 2019/6/17 18:08
 */
public class SamplePropertiesValidator implements Validator {

    private static final Pattern PATTERN = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");

    @Override
    public boolean supports(Class<?> type) {
        return type == SampleProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");
        SampleProperties properties = (SampleProperties) o;
        if (null != properties.getHost()
                && !PATTERN.matcher(properties.getHost()).matches()) {
            errors.rejectValue("host", "Invalid host", "Need match '^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$'");
        }
    }
}