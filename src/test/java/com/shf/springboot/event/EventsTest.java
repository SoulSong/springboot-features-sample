package com.shf.springboot.event;

import com.shf.springboot.NotWebBaseTest;
import com.shf.springboot.event.async.AsyncEventPayload;
import com.shf.springboot.event.async.AsyncEventPublisher;
import com.shf.springboot.event.condition.ConditionEventPayload;
import com.shf.springboot.event.condition.ConditionEventPublisher;
import com.shf.springboot.event.order.OrderEventPayload;
import com.shf.springboot.event.sync.SyncEventPayload;
import com.shf.springboot.event.sync.SyncEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

@Slf4j
public class EventsTest extends NotWebBaseTest {
    @Autowired
    private GeneralEventPublisher generalEventPublisher;

    @Autowired
    private ConditionEventPublisher conditionEventPublisher;

    @Autowired
    private SyncEventPublisher syncEventPublisher;

    @Autowired
    private AsyncEventPublisher asyncEventPublisher;


    @SuppressWarnings({"unchecked"})
    @Test
    public void checkOrderEvent() {
        generalEventPublisher.publishEvent(new GeneralEvent<>(OrderEventPayload.builder().message("I am a order event.").build()));
    }

    @Test
    public void checkConditionEvent() {
        conditionEventPublisher.publishEvent(new GeneralEvent<>(ConditionEventPayload.builder().message("I am a condition event").header("foo").build()));
        conditionEventPublisher.publishEvent(new GeneralEvent<>(ConditionEventPayload.builder().message("I am a condition event").header("bar").build()));
        conditionEventPublisher.publishEvent(new GeneralEvent<>(ConditionEventPayload.builder().message("I am a condition event").header("foo").build()));
    }

    @Test
    public void checkSyncEvent() {
        IntStream.rangeClosed(1, 5).parallel().forEach(index -> {
            syncEventPublisher.publishEvent(new GeneralEvent<>(SyncEventPayload.builder().index(index).build()));
        });
    }

    @Test
    public void checkAsyncEvent() {
        IntStream.rangeClosed(1, 5).parallel().forEach(index -> {
            asyncEventPublisher.publishEvent(new GeneralEvent<>(AsyncEventPayload.builder().index(index).build()));
        });
    }
}