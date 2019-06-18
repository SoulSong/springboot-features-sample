package com.shf.springboot.controller;

import com.shf.springboot.conversion.IdDecoder;
import com.shf.springboot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 16:36
 */
@RestController
@RequestMapping(("/user"))
@Slf4j
public class UserController {

    /**
     * Test features as follow:
     * - JsonSerialize & JsonDeserialize
     * - Validation
     * - i18n
     *
     * @param user json-string
     * @return {@link User}
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(user.toString());
        return user;
    }

    /**
     * Test features as follow:
     * - Conversion & Converter
     * - JsonSerialize & JsonDeserialize
     *
     * @param id id
     * @return {@link User}
     */
    @GetMapping("{id}")
    public User get(@IdDecoder(required = true) @PathVariable(name = "id") String id) {
        //check decoder id with IdConverter.
        log.info("Get user by id[{}].", id);
        return new User(id, "foo", "bar", 12, 120.5, "foo@gmail.com");
    }

}
