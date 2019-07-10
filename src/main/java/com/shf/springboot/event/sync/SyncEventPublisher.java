package com.shf.springboot.event.sync;

import com.shf.springboot.event.AbstractEventPublisher;
import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 16:45
 */
@Slf4j
@Component
public class SyncEventPublisher extends AbstractEventPublisher<SyncEventPayload> {

    @Override
    public void beforePublish(@NotNull GeneralEvent<SyncEventPayload> event) {
        log.info("Publish an event with index[{}].", event.getPayload().getIndex());
    }
}
