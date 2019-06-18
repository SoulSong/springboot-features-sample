package com.shf.springboot.exception.advice.throwable;

import com.shf.springboot.exception.ApplicationException;
import com.shf.springboot.exception.advice.ExceptionAdviceTrait;
import com.shf.springboot.exception.entity.UnknownErrorEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:45
 */
public interface ThrowableAdviceTrait extends ExceptionAdviceTrait {

    /**
     * handle normal application exception
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return error entity
     */
    @ExceptionHandler(Throwable.class)
    default Object handleThrowable(final HttpServletRequest request, final HttpServletResponse response,
                                   final Throwable exception) {
        String errorMessage = ApplicationException.DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE + ExceptionUtils.getStackTrace(exception);

        UnknownErrorEntity errorEntity = new UnknownErrorEntity(errorMessage);

        /*
         * Writing error stacktrace to logs with tracking id next to it
         */
        LOGGER.error("Unhandled error during request processing (tracking id: [{}]):",
                errorEntity.getTrackingId(), exception);

        return handle(request, response, ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorEntity));
    }

}
