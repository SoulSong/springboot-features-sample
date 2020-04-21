package com.shf.springboot.exception.advice.validation;

import com.shf.springboot.exception.ValidationException;
import com.shf.springboot.exception.advice.ExceptionAdviceTrait;
import com.shf.springboot.exception.entity.ValidationErrorEntity;
import com.shf.springboot.validator.ValidatorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.logging.Level;

/**
 * Description:
 * It works only under original JSR303 bean validation framework.
 * <p>
 * Note:
 * - this exception will be convert to org.springframework.web.bind.MethodArgumentNotValidException if using spring mvc
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 02:39
 */
public interface ConstraintViolationExceptionAdviceTrait extends ExceptionAdviceTrait {

    /**
     * handle java bean validation exception
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return error entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    default Object handle(final HttpServletRequest request, final HttpServletResponse response,
                          final ConstraintViolationException exception) {
        log(Level.FINEST, exception);

        ValidationException ex = toValidationException(exception);
        return handle(request, response, ResponseEntity.status(ex.getStatusCode()).body(new ValidationErrorEntity(ex)));
    }

    /**
     * create ValidationException
     *
     * @param exception ConstraintViolationException
     * @return ValidationException
     */
    default ValidationException toValidationException(ConstraintViolationException exception) {
        return new ValidationException(exception, ValidatorUtils.buildValidationMap(exception.getConstraintViolations()));
    }

}
