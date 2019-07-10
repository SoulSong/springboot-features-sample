package com.shf.springboot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 14:07
 */
public class GeneralEvent<T extends BaseEventPayload> extends ApplicationEvent implements ResolvableTypeProvider {

    public GeneralEvent(T payload) {
        super(payload);
    }

    @SuppressWarnings({"unchecked"})
    public T getPayload() {
        return (T) getSource();
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
