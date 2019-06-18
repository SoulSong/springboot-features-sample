package com.shf.springboot.entity;

import javax.validation.ConstraintValidatorContext;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/17 00:28
 */
public abstract class BaseEntity {

    public boolean validate(ConstraintValidatorContext context) {
        return true;
    }

    /**
     * Convenience method for adding a ConstraintViolation to the given context.
     *
     * @param context the ConstraintValidatorContext
     * @param message the message to attach to the violation
     */
    protected void violation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
