package com.shf.springboot.task.decorator;

import org.springframework.core.task.TaskDecorator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Description:
 * Define a task_decorator_chain for combine all customized task decorator.
 *
 * @author: songhaifeng
 * @date: 2019/10/8 13:36
 */
public class AsyncTaskDecorator implements TaskDecorator {
    private final Collection<ThreadTaskDecorator> decorators;

    public AsyncTaskDecorator(Collection<ThreadTaskDecorator> decorators) {
        this.decorators = decorators;
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        return new AsyncTaskDecoratorChain(decorators.iterator(), runnable).decoratorRunnable();
    }

    private final class AsyncTaskDecoratorChain {
        private final Iterator<ThreadTaskDecorator> decorators;
        private final Runnable runnable;

        AsyncTaskDecoratorChain(Iterator<ThreadTaskDecorator> decorators, Runnable runnable) {
            this.decorators = decorators;
            this.runnable = runnable;
        }

        Runnable decoratorRunnable() {
            Runnable delegate = runnable;
            while (decorators.hasNext()) {
                delegate = decorators.next().decorator(delegate);
            }
            return delegate;
        }
    }

}
