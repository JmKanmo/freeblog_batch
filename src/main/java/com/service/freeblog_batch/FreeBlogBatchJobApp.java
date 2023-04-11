package com.service.freeblog_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class FreeBlogBatchJobApp {
    public static void main(String[] args) {
        SpringApplication.run(FreeBlogBatchJobApp.class, args);
    }
}
