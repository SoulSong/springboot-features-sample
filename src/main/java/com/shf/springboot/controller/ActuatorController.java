package com.shf.springboot.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Test for metrics and logging
 *
 * @Author: songhaifeng
 * @Date: 2019/6/30 02:28
 */
@RestController
@Slf4j
@RequestMapping(value = "/actuator")
public class ActuatorController {

    @Timed
    @GetMapping(value = "logging/{user_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String logging(@PathVariable(value = "user_id") String userId) {
        final String message = String.format("hello %s", userId);
        log.info(message);
        return message;
    }

}
