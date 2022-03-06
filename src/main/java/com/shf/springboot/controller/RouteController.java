package com.shf.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.shf.springboot.resttemplate.ReconstructURIClientHttpRequestInterceptor.ROUTE;

/**
 * description :
 * 基于httpClient拦截器实现动态路由，此场景可用于灰度发布场景
 *
 * @author songhaifeng
 * @date 2022/3/6 13:33
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    @Qualifier("balanceRestTemplate")
    private RestTemplate balanceRestTemplate;

    @PostMapping("origin")
    public String origin() {
        return "origin";
    }

    @PostMapping("grey")
    public String grey() {
        return "grey";
    }

    @GetMapping("mock/{route}")
    public String balance(@PathVariable String route) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set(ROUTE, route);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        return balanceRestTemplate.exchange("http://localhost:8080/route", HttpMethod.POST, httpEntity, String.class).getBody();
    }
}
