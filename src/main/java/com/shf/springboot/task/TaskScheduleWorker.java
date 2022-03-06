package com.shf.springboot.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/9/30 15:06
 */
@Configuration
@EnableScheduling
@Slf4j
@ConditionalOnProperty(name = "schedule.enable",havingValue = "true",matchIfMissing = false)
public class TaskScheduleWorker {

    /**
     * task1
     */
    @Scheduled(cron = "${configs.service.task.worker1.cron}")
    public void work1() {
        log.info("work1");
    }

    /**
     * task2
     */
    @Scheduled(cron = "${configs.service.task.worker2.cron}")
    public void work2() {
        log.info("work2");
    }
}
