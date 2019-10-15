package com.shf.springboot.task.decorator;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/10/8 13:41
 */
public interface ThreadTaskDecorator {

    /**
     * decorator the runnable
     *
     * @param runnable runnable
     * @return runnable
     */
    Runnable decorator(Runnable runnable);

}
