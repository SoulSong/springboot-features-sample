package com.shf.springboot;

import com.shf.springboot.properties.SampleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songhaifeng
 */
@SpringBootApplication
@Slf4j
public class SampleApplication implements CommandLineRunner {

    @Autowired
    private SampleProperties sampleProperties;

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    /**
     * Test @ConfigurationProperties Validation
     *
     * @param args args
     */
    @Override
    public void run(String... args) {
        log.info("=========================================");
        log.info("Sample host: " + this.sampleProperties.getHost());
        log.info("Sample port: " + this.sampleProperties.getPort());
        log.info("Sample username: " + this.sampleProperties.getSecurity().getUsername());
        log.info("=========================================");
    }
}
