package com.shf.springboot.event.condition;

import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Description:
 * Order from small to large
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 11:13
 */
@Configuration
@Slf4j
public class ConditionEventListeners {

    @EventListener(condition = "#event.payload.header == 'foo'")
    public void processConditionEventWithFooHeader(GeneralEvent<ConditionEventPayload> event) {
        assert "foo".equals(event.getPayload().getHeader());
        log.info("Foo-Receiver event : {} with header[{}]", event.getPayload().getMessage(), event.getPayload().getHeader());
    }

    @EventListener(condition = "#event.payload.header == 'bar'")
    public void processConditionEventWithBarHeader(GeneralEvent<ConditionEventPayload> event) {
        assert "bar".equals(event.getPayload().getHeader());
        log.info("Bar-Receiver event : {} with header[{}]", event.getPayload().getMessage(), event.getPayload().getHeader());
    }
}
