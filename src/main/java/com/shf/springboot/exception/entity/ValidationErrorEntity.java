package com.shf.springboot.exception.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shf.springboot.exception.ValidationException;
import lombok.Data;

import java.util.Map;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 23:30
 */
@Data
@JsonPropertyOrder(value = {"bizErrorCode", "errorMessage", "errorCount", "validationFailures", "path", "timestamp"})
public class ValidationErrorEntity extends ErrorEntity {

    protected Map<String, FieldValidationFailure> validationFailures;
    private int errorCount;

    public ValidationErrorEntity(ValidationException validationException) {
        super(validationException);
        this.validationFailures = validationException.getValidationFailures();
        this.errorCount = validationException.getValidationFailures().size();
    }

}
