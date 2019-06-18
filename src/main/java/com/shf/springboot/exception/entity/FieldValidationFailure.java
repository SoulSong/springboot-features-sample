package com.shf.springboot.exception.entity;

import com.shf.springboot.entity.BaseEntity;

import java.util.Map;

/**
 * Description:
 * Validation info of each filed.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 15:20
 */
public class FieldValidationFailure extends BaseEntity {

    private String fieldName;
    private String errorMessage;
    private String errorCode;
    private String rejectedValue;

    public FieldValidationFailure(String fieldName, String errorMessage, String errorCode, String rejectedValue, Map<String, Object> messageVars) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.rejectedValue = rejectedValue;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

}
