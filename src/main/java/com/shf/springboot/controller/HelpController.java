package com.shf.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * Description:
 * Provide decoder and encoder endpoint.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 17:56
 */
@RestController
@RequestMapping("/help")
@Slf4j
public class HelpController {

    @GetMapping("base64/decoder/{message}")
    public String decoder(@PathVariable String message) {
        return new String(Base64Utils.decodeFromString(message), Charset.defaultCharset());
    }

    @GetMapping("base64/encoder/{message}")
    public String encoder(@PathVariable String message) {
        return Base64Utils.encodeToString(message.getBytes(Charset.defaultCharset()));
    }
}
