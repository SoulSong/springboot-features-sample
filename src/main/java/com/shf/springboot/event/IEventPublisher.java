package com.shf.springboot.event;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 13:52
 */
@Validated
public interface IEventPublisher<T extends BaseEventPayload> {

    void publishEvent(@NotNull GeneralEvent<T> event);

    default void beforePublish(@NotNull GeneralEvent<T> event) {
    }

    default void afterPublish(@NotNull GeneralEvent<T> event) {
    }
}
