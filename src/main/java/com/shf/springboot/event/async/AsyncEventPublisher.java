package com.shf.springboot.event.async;

import com.shf.springboot.event.AbstractEventPublisher;
import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 21:00
 */
@Component
@Slf4j
public class AsyncEventPublisher extends AbstractEventPublisher<AsyncEventPayload> {

    @Override
    public void beforePublish(@NotNull GeneralEvent<AsyncEventPayload> event) {
        log.info("Publish an event with index[{}].", event.getPayload().getIndex());
    }
}
