package com.shf.springboot.exception;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpStatus;

import static com.shf.springboot.exception.DefaultBizErrorCode.DEFAULT_ERROR_CODE;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 17:40
 */
public abstract class ApplicationException extends AbstractException {

    private static final int DEFAULT_APPLICATION_EXCEPTION_STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final String DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE = "An error occurred and we were unable to resolve it, please contact support on customer service.";

    /**
     * Mapping http status code
     */
    private final int statusCode;
    private final int bizErrorCode;
    private DateTime timestamp;

    protected ApplicationException(Exception e) {
        this(e, DEFAULT_APPLICATION_EXCEPTION_STATUS_CODE, DEFAULT_ERROR_CODE, DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE);
    }

    protected ApplicationException(Exception e, String pattern, Object... args) {
        this(e, DEFAULT_APPLICATION_EXCEPTION_STATUS_CODE, DEFAULT_ERROR_CODE, DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE, pattern, args);
    }

    protected ApplicationException(Exception e, int status, int bizErrorCode, String pattern, Object... args) {
        super(e, pattern, args);
        this.statusCode = status;
        this.bizErrorCode = bizErrorCode;
        this.timestamp = DateTime.now(DateTimeZone.UTC);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public int getBizErrorCode() {
        return bizErrorCode;
    }

    public DateTime getTimestamp() {
        return this.timestamp;
    }

}
