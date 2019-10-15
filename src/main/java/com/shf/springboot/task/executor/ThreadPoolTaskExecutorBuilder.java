package com.shf.springboot.task.executor;


import com.shf.springboot.task.decorator.AsyncTaskDecorator;
import com.shf.springboot.task.decorator.ThreadTaskDecorator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * A builder for creating a thread-pool-executor instance with the {@link ThreadTaskDecorator} list.
 *
 * @author: songhaifeng
 * @date: 2019/10/16 02:24
 */
@Slf4j
public class ThreadPoolTaskExecutorBuilder {
    private List<ThreadTaskDecorator> decorators;
    private static final String SYMBOL = "-";

    public ThreadPoolTaskExecutorBuilder(List<ThreadTaskDecorator> decorators) {
        this.decorators = decorators;
    }

    /**
     * create a {@link ThreadPoolTaskExecutor} instance.
     *
     * @param threadPoolExecutorProperty threadPoolExecutorProperty
     * @return ThreadPoolTaskExecutor
     */
    public ThreadPoolTaskExecutor build(ThreadPoolExecutorProperty threadPoolExecutorProperty) {
        final String threadName = threadPoolExecutorProperty.getThreadName();
        if (StringUtils.isBlank(threadName)) {
            throw new IllegalArgumentException("ThreadPoolExecutorProperty’s thread-name must not be blank.");
        }
        log.info("Initialize customized thead_pool:{}", threadPoolExecutorProperty.toString());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        if (CollectionUtils.isNotEmpty(decorators)) {
            executor.setTaskDecorator(new AsyncTaskDecorator(decorators));
        }
        executor.setBeanName(threadPoolExecutorProperty.getThreadName());
        executor.setCorePoolSize(threadPoolExecutorProperty.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolExecutorProperty.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolExecutorProperty.getQueueCapacity());
        executor.setThreadNamePrefix(threadName + SYMBOL);
        executor.setKeepAliveSeconds(threadPoolExecutorProperty.getKeepAliveSeconds());
        executor.setWaitForTasksToCompleteOnShutdown(threadPoolExecutorProperty.isWaitForTasksToCompleteOnShutdown());
        executor.setThreadPriority(threadPoolExecutorProperty.getThreadPriority());
        executor.setRejectedExecutionHandler(threadPoolExecutorProperty.getRejectedExecutionHandler());
        executor.setAllowCoreThreadTimeOut(threadPoolExecutorProperty.isAllowCoreThreadTimeOut());
        executor.setAwaitTerminationSeconds(threadPoolExecutorProperty.getAwaitTerminationSeconds());
        executor.initialize();
        return executor;
    }
}
