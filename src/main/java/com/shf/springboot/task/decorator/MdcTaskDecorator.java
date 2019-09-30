package com.shf.springboot.task.decorator;

import org.apache.commons.collections.MapUtils;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Description:
 * MDC task decorator
 *
 * @author: songhaifeng
 * @date: 2019/10/8 13:40
 */
public final class MdcTaskDecorator implements RunnableTaskDecorator {

    @Override
    public Runnable decorator(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            if (MapUtils.isNotEmpty(contextMap)) {
                try {
                    MDC.setContextMap(contextMap);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            } else {
                runnable.run();
            }
        };
    }
}
