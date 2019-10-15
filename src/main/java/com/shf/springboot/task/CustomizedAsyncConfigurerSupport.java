package com.shf.springboot.task;

import com.shf.springboot.task.decorator.AsyncTaskDecorator;
import com.shf.springboot.task.decorator.MdcTaskDecorator;
import com.shf.springboot.task.decorator.RequestAttributesTaskDecorator;
import com.shf.springboot.task.decorator.ThreadTaskDecorator;
import com.shf.springboot.task.executor.ThreadPoolExecutorProperty;
import com.shf.springboot.task.executor.ThreadPoolTaskExecutorBuilder;
import com.shf.springboot.task.filter.MdcFilter;
import com.shf.springboot.task.filter.RequestAttributesFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Description:
 * Customize async config and register the customized thread_pool_executor.
 *
 * @author: songhaifeng
 * @date: 2019/10/8 14:27
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class CustomizedAsyncConfigurerSupport extends AsyncConfigurerSupport {
    public static final String CUSTOMIZED_TASK_EXECUTOR = "customizedTaskExecutor";

    @Autowired(required = false)
    private List<ThreadTaskDecorator> decoratorList = new ArrayList<>();

    @Bean
    public ThreadTaskDecorator mdcTaskDecorator() {
        return new MdcTaskDecorator();
    }

    @Bean
    public ThreadTaskDecorator requestAttributesTaskDecorator() {
        return new RequestAttributesTaskDecorator();
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new AsyncTaskDecorator(decoratorList));
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("context-decorator-");
        executor.initialize();
        return executor;
    }

    @Bean
    public FilterRegistrationBean mdcFilter() {
        MdcFilter mdcFilter = new MdcFilter();
        FilterRegistrationBean<MdcFilter> filterRegistrationBean = new FilterRegistrationBean<>(mdcFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
        filterRegistrationBean.setOrder(OrderValue.MDC_FILTER_ORDER);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean requestAttributesFilter() {
        RequestAttributesFilter requestAttributesFilter = new RequestAttributesFilter();
        FilterRegistrationBean<RequestAttributesFilter> filterRegistrationBean = new FilterRegistrationBean<>(requestAttributesFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
        return filterRegistrationBean;
    }

    @Bean(name = CUSTOMIZED_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor customizedTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new AsyncTaskDecorator(decoratorList));
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(5);
        executor.setThreadNamePrefix("customized-pool-");
        executor.initialize();
        return executor;
    }

    /*************************create thread pool with builder****************************/
    public static final String THREAD_POOL_NAME = "customized-pool2";

    @Bean
    public ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder() {
        return new ThreadPoolTaskExecutorBuilder(decoratorList);
    }

    @Bean(THREAD_POOL_NAME)
    public ThreadPoolTaskExecutor customizedTaskExecutor2(ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder) {
        return threadPoolTaskExecutorBuilder.build(new ThreadPoolExecutorProperty()
                .setCorePoolSize(3)
                .setMaxPoolSize(3)
                .setThreadName(THREAD_POOL_NAME));
    }
}
