package com.shf.springboot.exception.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.shf.springboot.entity.BaseEntity;
import com.shf.springboot.exception.ApplicationException;
import com.shf.springboot.exception.DefaultBizErrorCode;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

/**
 * Description:
 * Basic error info
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 16:40
 */
@Data
@JsonPropertyOrder(value = {"bizErrorCode", "errorMessage", "path", "timestamp"})
public class ErrorEntity extends BaseEntity {
    private int bizErrorCode;
    private String errorMessage;
    /**
     * @JsonFormat and @DateTimeFormat all work
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp;
    private String path;

    public ErrorEntity() {
        this.bizErrorCode = DefaultBizErrorCode.DEFAULT_ERROR_CODE;
        this.errorMessage = ApplicationException.DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE;
        this.timestamp = DateTime.now(DateTimeZone.UTC).toDate();
        setPath();
    }

    public ErrorEntity(String errMessage) {
        this.bizErrorCode = DefaultBizErrorCode.DEFAULT_ERROR_CODE;
        this.errorMessage = errMessage;
        this.timestamp = DateTime.now(DateTimeZone.UTC).toDate();
        setPath();
    }

    public ErrorEntity(int bizErrorCode, String errMessage) {
        this.bizErrorCode = bizErrorCode;
        this.errorMessage = errMessage;
        this.timestamp = DateTime.now(DateTimeZone.UTC).toDate();
        setPath();
    }

    public ErrorEntity(ApplicationException applicationException) {
        this.bizErrorCode = applicationException.getBizErrorCode();
        this.errorMessage = applicationException.getMessage();
        this.timestamp = applicationException.getTimestamp().toDate();
        setPath();
    }

    /**
     * get the mapping handler
     */
    private void setPath() {
        this.path = (String) RequestContextHolder.currentRequestAttributes().getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping", 0);
    }

}
