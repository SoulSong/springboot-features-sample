package com.shf.springboot.exception;

import com.shf.springboot.exception.entity.FieldValidationFailure;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shf.springboot.exception.DefaultBizErrorCode.VALIDATION_ERROR_CODE;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 22:46
 */
public class ValidationException extends ApplicationException {

    private static final int DEFAULT_VALIDATION_EXCEPTION_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
    private static final String DEFAULT_VALIDATION_EXCEPTION_ERROR_MESSAGE = "Validation failed.";
    private final Map<String, FieldValidationFailure> validationFailures = new HashMap<>();

    public ValidationException(Exception e, Map<String, FieldValidationFailure> fieldValidationFailures) {
        super(e, DEFAULT_VALIDATION_EXCEPTION_STATUS_CODE, VALIDATION_ERROR_CODE, DEFAULT_VALIDATION_EXCEPTION_ERROR_MESSAGE);
        if (fieldValidationFailures != null) {
            this.validationFailures.putAll(fieldValidationFailures);
        }
    }

    public Map<String, FieldValidationFailure> getValidationFailures() {
        return this.validationFailures;
    }

    @Override
    public String toString() {
        String message = super.toString();
        final ArrayList<String> list = new ArrayList<>(this.validationFailures.size());
        list.addAll(this.validationFailures.values().stream().map(FieldValidationFailure::getErrorMessage).collect(Collectors.toList()));
        message = MessageFormatter.arrayFormat(message + " {}", new Object[]{list}).getMessage();
        return message;
    }

}
