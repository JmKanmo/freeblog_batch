package com.jmblog.freeblog_batch.job;

import com.jmblog.freeblog_batch.job.step.NoSqlProcessStep;
import com.jmblog.freeblog_batch.job.step.RDBProcessStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableBatchProcessing
public class FreeBlogBatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RDBProcessStep RDBProcessStep;

    @Autowired
    private NoSqlProcessStep noSqlProcessStep;

    @Bean
    public Job processJob() {
        return this.jobBuilderFactory.get("processJob")
                .start(RDBProcessStep.dbCleanStep())
                .next(noSqlProcessStep.noSqlCleanStep())
                .build();
    }
}
