package com.shf.springboot.task.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

import static com.shf.springboot.task.CustomizedAsyncConfigurerSupport.CUSTOMIZED_TASK_EXECUTOR;

/**
 * Description:
 * Log context
 *
 * @author: songhaifeng
 * @date: 2019/10/8 14:42
 */
public interface ContextLoggerService {
    /**
     * log context with the default executor.
     */
    @Async
    void logContext1();

    /**
     * log context with the customized executor.
     */
    @Async(value = CUSTOMIZED_TASK_EXECUTOR)
    void logContext2();

    /**
     * log context manually
     */
    Future<String> logContext3();

    /**
     * log context with another customized executor.
     */
    Future<String> logContext4();

    /**
     * log context with CompletableFuture.
     */
    Future<String> logContext5();

}
