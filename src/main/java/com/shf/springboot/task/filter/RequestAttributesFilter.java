package com.shf.springboot.task.filter;

import com.shf.springboot.task.OrderValue;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * Description:
 * Must register after {@link OrderedRequestContextFilter}
 *
 * @author: songhaifeng
 * @date: 2019/10/8 15:57
 */
@Slf4j
public class RequestAttributesFilter extends OncePerRequestFilter implements OrderedFilter {
    public static final String X_REQUEST_FROM = "x-request-from";
    public static final String ANONYMOUS = "anonymous";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestContextHolder.currentRequestAttributes().setAttribute(X_REQUEST_FROM, "abc", SCOPE_REQUEST);
        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return OrderValue.REQUEST_ATTRIBUTE_FILTER_ORDER;
    }
}
