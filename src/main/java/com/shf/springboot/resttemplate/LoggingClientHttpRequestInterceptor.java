package com.shf.springboot.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Add a request interceptor sample for rest-template.
 *
 * @author: songhaifeng
 * @date: 2019/9/30 11:09
 */
@Slf4j
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("Logging before request");

        final ClientHttpResponse response = execution.execute(request, body);

        log.info("Logging after get response");
        return response;
    }
}
