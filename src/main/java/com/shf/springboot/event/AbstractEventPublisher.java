package com.shf.springboot.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 11:06
 */
public abstract class AbstractEventPublisher<T extends BaseEventPayload> implements ApplicationEventPublisherAware, IEventPublisher<T> {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(GeneralEvent<T> event) {
        beforePublish(event);
        publisher.publishEvent(event);
        afterPublish(event);
    }

}
