package com.shf.springboot.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 * Default thread pool size is 1. Look into {@link TaskSchedulingAutoConfiguration} and {@link TaskSchedulingProperties}.
 * As the same, task execution configuration  looks into {@link TaskExecutionAutoConfiguration}.
 * Here is the hard-code mode to customized the thread pool size, spring also provides the properties to config,
 * config as 'spring.task.scheduling.pool.size=2' in the property file.
 *
 * @Author: songhaifeng
 * @Date: 2019/5/8 17:24
 */
@Configuration
public class CustomizeSchedulingConfigurer implements SchedulingConfigurer {

    @Value("${configs.scheduler.thread.size:2}")
    private int threadSize;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor taskExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("schedule-pool-%d").build();

        return new ScheduledThreadPoolExecutor(threadSize, namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
