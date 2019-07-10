package com.shf.springboot.event.async;

import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 21:06
 */
@Configuration
@Slf4j
@EnableAsync
public class AsyncEventListeners {

    @EventListener
    @Async
    public void processAsyncEvent(GeneralEvent<AsyncEventPayload> event) {
        log.info("Receive the event with index[{}]", event.getPayload().getIndex());
    }

}
