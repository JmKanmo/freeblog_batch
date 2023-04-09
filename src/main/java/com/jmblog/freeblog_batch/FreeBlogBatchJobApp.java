package com.jmblog.freeblog_batch;

import com.jmblog.freeblog_batch.job.step.RDBProcessStep;
import com.jmblog.freeblog_batch.job.step.NoSqlProcessStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class FreeBlogBatchJobApp {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RDBProcessStep RDBProcessStep;

    @Autowired
    private NoSqlProcessStep noSqlProcessStep;

    @Bean
    public Job freeBlogBatchJob() {
        return this.jobBuilderFactory.get("freeBlogBatchJob")
                .start(RDBProcessStep.dbCleanStep())
                .next(noSqlProcessStep.noSqlCleanStep())
                .build();
    }

    @Bean
    public Step initializeBatch() {
        return this.stepBuilderFactory.get("initializeBatch")
                .job(freeBlogBatchJob())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(FreeBlogBatchJobApp.class, args);
    }
}
