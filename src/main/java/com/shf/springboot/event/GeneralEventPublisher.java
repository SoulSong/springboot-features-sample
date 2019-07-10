package com.shf.springboot.event;

import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 13:14
 */
@Component
public class GeneralEventPublisher<T extends BaseEventPayload> extends AbstractEventPublisher<T> {
}
