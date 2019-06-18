package com.shf.springboot.exception.advice.validation;

import com.shf.springboot.exception.ValidationException;
import com.shf.springboot.exception.advice.ExceptionAdviceTrait;
import com.shf.springboot.exception.entity.FieldValidationFailure;
import com.shf.springboot.exception.entity.ValidationErrorEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/18 10:07
 */
public interface MethodArgumentNotValidExceptionAdviceTrait extends ExceptionAdviceTrait {

    /**
     * handle Method Argument Not Valid exception
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return error entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default Object handle(final HttpServletRequest request, final HttpServletResponse response,
                          final MethodArgumentNotValidException exception) {
        log(Level.FINEST, exception);

        BindingResult result = exception.getBindingResult();

        final List<FieldValidationFailure> violations = Stream.concat(
                result.getFieldErrors().stream().map(this::createViolation),
                result.getGlobalErrors().stream().map(this::createViolation))
                .collect(toList());

        Map<String, FieldValidationFailure> fields = new HashMap<>(violations.size());

        for (FieldValidationFailure violation : violations) {
            fields.put(violation.getFieldName(), violation);
        }

        ValidationException ex = new ValidationException(fields);
        return handle(request, response, ResponseEntity.status(ex.getStatusCode()).body(new ValidationErrorEntity(ex)));
    }

    /**
     * create FieldValidationFailure from FiledError
     *
     * @param error FieldError
     * @return FieldValidationFailure
     */
    default FieldValidationFailure createViolation(final FieldError error) {
        final String fieldName = error.getObjectName() + "." + error.getField();
        return new FieldValidationFailure(fieldName, error.getDefaultMessage(), error.getCode(), String.valueOf(error.getRejectedValue()), null);
    }

    /**
     * create FieldValidationFailure from ObjectError
     *
     * @param error ObjectError
     * @return FieldValidationFailure
     */
    default FieldValidationFailure createViolation(final ObjectError error) {
        final String fieldName = error.getObjectName();
        return new FieldValidationFailure(fieldName, error.getDefaultMessage(), error.getCode(), null, null);
    }


}