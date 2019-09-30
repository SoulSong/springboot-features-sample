package com.shf.springboot.task.service.impl;

import com.shf.springboot.task.filter.RequestAttributesFilter;
import com.shf.springboot.task.service.ContextLoggerService;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import static com.shf.springboot.task.CustomizedAsyncConfigurerSupport.CUSTOMIZED_TASK_EXECUTOR;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/10/8 14:43
 */
@Slf4j
@Service
public class ContextLoggerServiceImpl implements ContextLoggerService {
    @Autowired
    @Qualifier(CUSTOMIZED_TASK_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void logContext1() {
        logContext();
    }

    @Override
    public void logContext2() {
        logContext();
    }

    @Override
    public Future<String> logContext3() {
        return threadPoolTaskExecutor.submit(() -> {
            logContext();
            return "OK";
        });
    }

    private void logContext() {
        log.info("executorThreadId: {}; ContextMap on execution: {}; Request from on execution: {}",
                Thread.currentThread().getId(),
                MDC.getCopyOfContextMap(),
                RequestContextHolder.getRequestAttributes() != null ?
                        RequestContextHolder.getRequestAttributes().getAttribute(RequestAttributesFilter.X_REQUEST_FROM, RequestAttributes.SCOPE_REQUEST) :
                        RequestAttributesFilter.ANONYMOUS);
    }
}
