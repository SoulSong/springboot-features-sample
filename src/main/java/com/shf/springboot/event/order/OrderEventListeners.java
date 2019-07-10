package com.shf.springboot.event.order;

import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

/**
 * Description:
 * Listeners receive in small to large order.
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 11:13
 */
@Configuration
@Slf4j
public class OrderEventListeners {

    @EventListener
    @Order(1)
    public void processOrderEvent1(GeneralEvent<OrderEventPayload> event) {
        log.info("No {} listener, receive event : {}", "1", event.getPayload().getMessage());
    }

    @EventListener
    @Order(2)
    public void processOrderEvent2(GeneralEvent<OrderEventPayload> event) {
        log.info("No {} listener, receive event : {}", "2", event.getPayload().getMessage());
    }

    @EventListener
    @Order(3)
    public void processOrderEvent3(GeneralEvent<OrderEventPayload> event) {
        log.info("No {} listener, receive event : {}", "3", event.getPayload().getMessage());
    }

}
