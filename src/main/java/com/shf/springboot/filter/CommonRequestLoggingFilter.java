package com.shf.springboot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 * Logging some important information for each request, it is useful for analysis.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/30 02:14
 */
@Slf4j
public final class CommonRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            super.doFilterInternal(request, response, filterChain);
        } finally {
            stopWatch.stop();
            log.info("remote:[{}],uri:[{}],api:[{}],http-method:[{}],spent:[{}]", request.getRemoteAddr(), request.getRequestURI(),
                    request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"), request.getMethod(), stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {
    }
}