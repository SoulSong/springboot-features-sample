package com.shf.springboot.validator;

import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description:
 * Validate string by {@link EmailValidator},
 * invoke {@link org.springframework.web.bind.MethodArgumentNotValidException}
 *
 * @Author: songhaifeng
 * @Date: 2019/6/16 17:52
 */
public class EmailConstraintValidator implements ConstraintValidator<EmailValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        return EmailValidator.getInstance(true).isValid(value);
    }
}
