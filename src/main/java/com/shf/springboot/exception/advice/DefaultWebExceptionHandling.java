package com.shf.springboot.exception.advice;

import com.shf.springboot.exception.advice.throwable.GeneralExceptionAdviceTrait;
import com.shf.springboot.exception.advice.validation.ValidationExceptionAdviceTrait;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:43
 */
@ControllerAdvice
public class DefaultWebExceptionHandling implements ValidationExceptionAdviceTrait, GeneralExceptionAdviceTrait {
    @Override
    public boolean isSupported(Throwable error) {
        return true;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        return handleThrowable(request, response, exception);
    }
}
