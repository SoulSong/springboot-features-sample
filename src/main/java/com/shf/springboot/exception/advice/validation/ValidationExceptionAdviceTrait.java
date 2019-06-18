package com.shf.springboot.exception.advice.validation;

import com.shf.springboot.exception.ValidationException;
import com.shf.springboot.exception.entity.ValidationErrorEntity;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:36
 */
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public interface ValidationExceptionAdviceTrait extends
        MethodArgumentNotValidExceptionAdviceTrait,
        ConstraintViolationExceptionAdviceTrait {

    /**
     * handle Customized validation application exception
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return error entity
     */
    @ExceptionHandler(ValidationException.class)
    default Object handle(final HttpServletRequest request, final HttpServletResponse response,
                          final ValidationException exception) {
        log(Level.FINEST, exception);
        return handle(request, response, ResponseEntity.status(exception.getStatusCode()).body(new ValidationErrorEntity(exception)));
    }

}
