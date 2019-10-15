package com.shf.springboot.task.executor;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 * All customizable properties for thread-pool-executor.
 *
 * @author: songhaifeng
 * @date: 2019/10/16 11:06
 */
public class ThreadPoolExecutorProperty {

    private int corePoolSize = 1;
    private int maxPoolSize = 2147483647;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 2147483647;
    private boolean allowCoreThreadTimeOut = false;
    private boolean waitForTasksToCompleteOnShutdown = false;
    private int awaitTerminationSeconds = 0;
    private String threadName;
    private int threadPriority = Thread.NORM_PRIORITY;
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public ThreadPoolExecutorProperty setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public ThreadPoolExecutorProperty setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public ThreadPoolExecutorProperty setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
        return this;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public ThreadPoolExecutorProperty setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
        return this;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public ThreadPoolExecutorProperty setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    public boolean isWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public ThreadPoolExecutorProperty setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
        return this;
    }

    public int getAwaitTerminationSeconds() {
        return awaitTerminationSeconds;
    }

    public ThreadPoolExecutorProperty setAwaitTerminationSeconds(int awaitTerminationSeconds) {
        this.awaitTerminationSeconds = awaitTerminationSeconds;
        return this;
    }

    public String getThreadName() {
        return threadName;
    }

    public ThreadPoolExecutorProperty setThreadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public ThreadPoolExecutorProperty setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
        return this;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public ThreadPoolExecutorProperty setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        return this;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
