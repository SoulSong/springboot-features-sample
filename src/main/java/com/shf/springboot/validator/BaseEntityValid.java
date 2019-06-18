/*
 * Copyright (c) 2016 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.shf.springboot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotate entity which needs to validate by {@link BaseObjectValidator}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
@Constraint(validatedBy = BaseObjectValidator.class)
public @interface BaseEntityValid {
    String message() default "entity.invalid.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
