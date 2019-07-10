package com.shf.springboot.event.sync;

import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 16:41
 */
@Slf4j
@Configuration
public class SyncEventListeners {

    @EventListener
    public void processSyncEvent(GeneralEvent<SyncEventPayload> event) {
        log.info("Receive the event with index[{}]", event.getPayload().getIndex());
    }

}
