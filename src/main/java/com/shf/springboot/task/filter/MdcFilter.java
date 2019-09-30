package com.shf.springboot.task.filter;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Mock to add the userId with a uuid.
 *
 * @author: songhaifeng
 * @date: 2019/10/8 14:57
 */
@Slf4j
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String userId = UUID.randomUUID().toString();
            log.info("Mocked userID:{}", userId);
            MDC.put("userId", userId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
