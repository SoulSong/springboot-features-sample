package com.shf.springboot.validator;

import com.shf.springboot.exception.entity.FieldValidationFailure;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * description :
 * Support manual validation with validator.
 *
 * @author songhaifeng
 * @date 2020/4/21 16:06
 */
public class ValidatorUtils {

    /**
     * validator
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Validate all properties in an entity, return the failure messages in a {@link Map}.
     *
     * @param entity an entity which to be validated.
     * @return map
     */
    public static <T> Map<String, FieldValidationFailure> validateEntity(T entity) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(entity, Default.class);
        return buildValidationMap2(validateSet);
    }

    /**
     * Validate a specific property in an entity
     *
     * @param entity an entity which to be validated.
     * @param propertyName a specific property which to be validated.
     * @return map
     */
    public static <T> Map<String, FieldValidationFailure> validateProperty(T entity, String propertyName) {
        Set<ConstraintViolation<T>> validateSet = validator.validateProperty(entity, propertyName, Default.class);
        return buildValidationMap2(validateSet);
    }

    private static <T> Map<String, FieldValidationFailure> buildValidationMap2(Set<ConstraintViolation<T>> validateSet) {
        Map<String, FieldValidationFailure> validationMap = new HashMap<>(validateSet.size());
        for (ConstraintViolation<T> violation : validateSet) {
            String fieldName = violation.getPropertyPath().toString();
            ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
            FieldValidationFailure failure = new FieldValidationFailure(fieldName,
                    violation.getMessage(),
                    descriptor.getAnnotation().annotationType().getName(),
                    String.valueOf(violation.getInvalidValue()),
                    null);
            validationMap.put(fieldName, failure);
        }
        return validationMap;
    }

    public static Map<String, FieldValidationFailure> buildValidationMap(Set<ConstraintViolation<?>> validateSet) {
        Map<String, FieldValidationFailure> validationMap = new HashMap<>(validateSet.size());
        for (ConstraintViolation<?> violation : validateSet) {
            String fieldName = violation.getPropertyPath().toString();
            ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
            FieldValidationFailure failure = new FieldValidationFailure(fieldName,
                    violation.getMessage(),
                    descriptor.getAnnotation().annotationType().getName(),
                    String.valueOf(violation.getInvalidValue()),
                    null);
            validationMap.put(fieldName, failure);
        }
        return validationMap;
    }
}
