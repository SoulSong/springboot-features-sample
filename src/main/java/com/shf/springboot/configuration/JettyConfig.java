package com.shf.springboot.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.thread.MonitoredQueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * description :
 *
 * @author songhaifeng
 * @date 2021/10/9 16:57
 */
@Configuration
@Slf4j
public class JettyConfig {

    @Value("${configs.jetty.thread.pool.min:8}")
    private int jettyThreadPoolMin;
    @Value("${configs.jetty.thread.pool.max:200}")
    private int jettyThreadPoolMax;

    /**
     * {@code ServletWebServerFactoryConfiguration}
     *
     * @param jettyServerCustomizer jettyServerCustomizer
     * @return JettyServletWebServerFactory
     */
    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory(
            JettyServerCustomizer jettyServerCustomizer) {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        // enable thread pool with stats
        factory.setThreadPool(new MonitoredQueuedThreadPool());
        factory.addServerCustomizers(jettyServerCustomizer);
        return factory;
    }

    @Bean
    public JettyServerCustomizer jettyServerCustomizer() {
        return server -> {
            final MonitoredQueuedThreadPool threadPool = server.getBean(MonitoredQueuedThreadPool.class);
            threadPool.setMaxThreads(jettyThreadPoolMax);
            threadPool.setMinThreads(jettyThreadPoolMin);
        };
    }

    @EnableScheduling
    @Configuration
    class JettyMonitorTask {
        @Qualifier("jettyServletWebServerFactory")
        @Autowired
        private JettyServletWebServerFactory factory;
        @Autowired
        private ObjectMapper objectMapper;

        @Scheduled(fixedDelayString = "${configs.jetty.thread.monitor.period:1000}", initialDelay = 5000)
        private void monitor() throws JsonProcessingException {
            MonitoredQueuedThreadPool threadPool = (MonitoredQueuedThreadPool) factory.getThreadPool();
            if (log.isDebugEnabled()) {
                log.debug("JettyThreadState : {}", objectMapper.writeValueAsString(new JettyThreadState(
                        threadPool.getName(), threadPool.getMinThreads(),
                        threadPool.getMaxThreads(), threadPool.getQueueSize(),
                        threadPool.getMaxQueueLatency(), threadPool.getMaxTaskLatency(),
                        threadPool.getMaxBusyThreads(), threadPool.getIdleThreads(),
                        threadPool.getMaxQueueSize(), threadPool.getTasks())));
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class JettyThreadState {
        private String threadPoolName;
        private int minThreads;
        private int maxThreads;
        private int queueSize;
        private long maxQueueLatency;
        private long maxTaskLatency;
        private long maxBusyThreads;
        private long idleThreads;
        private long maxQueueSize;
        private long maxTasks;
    }
}
