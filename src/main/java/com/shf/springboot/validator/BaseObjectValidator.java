/*
 * Copyright (c) 2016 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.shf.springboot.validator;


import com.shf.springboot.entity.BaseEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate entity classes which extends {@link BaseEntity},
 * invoke {@link org.springframework.web.bind.MethodArgumentNotValidException}
 *
 * @author songhaifeng
 */
public class BaseObjectValidator
        implements ConstraintValidator<BaseEntityValid, BaseEntity> {
    @Override
    public void initialize(BaseEntityValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(BaseEntity entity, ConstraintValidatorContext context) {
        return entity.validate(context);
    }
}