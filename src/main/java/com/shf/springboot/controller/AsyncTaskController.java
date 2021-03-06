package com.shf.springboot.controller;

import com.shf.springboot.task.executor.ThreadPoolTaskExecutorBuilder;
import com.shf.springboot.task.service.ContextLoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Description:
 * Test for the customized threadPoolTaskExecutor
 *
 * @author: songhaifeng
 * @date: 2019/10/8 14:45
 */
@RestController
public class AsyncTaskController {
    @Autowired
    private ContextLoggerService contextLoggerService;

    /**
     * Test for the default executor with @Async annotation
     *
     * @return String
     */
    @GetMapping(value = "task1")
    public String task1() {
        contextLoggerService.logContext1();
        return "OK";
    }

    /**
     * Test for the customized executor with @Async annotation
     *
     * @return String
     */
    @GetMapping(value = "task2")
    public String task2() {
        contextLoggerService.logContext2();
        return "OK";
    }

    /**
     * Test for the customized executor to execute the callable manually.
     *
     * @return String
     */
    @GetMapping(value = "task3")
    public String task3() throws ExecutionException, InterruptedException {
        return contextLoggerService.logContext3().get();
    }

    /**
     * Test for the customized executor which is created by the customized {@link ThreadPoolTaskExecutorBuilder}.
     *
     * @return String
     */
    @GetMapping(value = "task4")
    public String task4() throws ExecutionException, InterruptedException {
        return contextLoggerService.logContext4().get();
    }

    /**
     * Test for the customized executor which is created by the customized {@link ThreadPoolTaskExecutorBuilder}
     * and submit by {@link CompletableFuture}.
     *
     * @return String
     */
    @GetMapping(value = "task5")
    public String task5() throws ExecutionException, InterruptedException {
        return contextLoggerService.logContext5().get();
    }
}
