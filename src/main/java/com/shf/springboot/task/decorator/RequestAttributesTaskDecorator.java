package com.shf.springboot.task.decorator;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Description:
 * Request attributes task decorator.
 *
 * @author: songhaifeng
 * @date: 2019/10/8 13:44
 */
public final class RequestAttributesTaskDecorator implements RunnableTaskDecorator {
    @Override
    public Runnable decorator(Runnable runnable) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }
}
