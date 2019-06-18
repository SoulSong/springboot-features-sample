package com.shf.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/18 00:55
 */
@RequestMapping("/i18n")
@RestController
@Slf4j
public class I18nController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "zh", produces = "application/json;charset=utf-8")
    public String getCNMessage() {
        return messageSource.getMessage("email.invalid.message", new Object[]{}, "default message", Locale.SIMPLIFIED_CHINESE);
    }

    @GetMapping(value = "us", produces = "application/json;charset=utf-8")
    public String getUSMessage() {
        return messageSource.getMessage("email.invalid.message", new Object[]{}, "default message", Locale.US);
    }

    @GetMapping(value = "default", produces = "application/json;charset=utf-8")
    public String getDefaultMessage() {
        return messageSource.getMessage("email.invalid.message", new Object[]{}, "default message", Locale.getDefault());
    }
}
