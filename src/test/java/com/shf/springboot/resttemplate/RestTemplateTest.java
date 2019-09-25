package com.shf.springboot.resttemplate;

import com.shf.springboot.WebBaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Description:
 * Test for rest-template {@link LoggingClientHttpRequestInterceptor} and {@link LoggingResponseErrorHandler}
 *
 * @author: songhaifeng
 * @date: 2019/9/30 11:48
 */
public class RestTemplateTest extends WebBaseTest {

    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test(expected = HttpServerErrorException.class)
    public void errorRequest() {
        restTemplate.postForEntity("http://127.0.0.1:" + port + "/help/base64/encoder/1", null, String.class);
    }


    @Test
    public void normalRequest() {
        restTemplate.getForObject("http://127.0.0.1:" + port + "/help/base64/encoder/1", String.class);
    }
}
