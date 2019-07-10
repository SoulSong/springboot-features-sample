package com.shf.springboot.event.condition;

import com.shf.springboot.event.AbstractEventPublisher;
import com.shf.springboot.event.GeneralEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 11:06
 */
@Component
@Slf4j
public class ConditionEventPublisher extends AbstractEventPublisher<ConditionEventPayload> {

    @Override
    public void beforePublish(@NotNull GeneralEvent<ConditionEventPayload> event) {
        log.info("Publish an event with header[{}].", event.getPayload().getHeader());
    }

}
