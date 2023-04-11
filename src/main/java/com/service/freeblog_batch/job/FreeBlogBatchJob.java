package com.service.freeblog_batch.job;

import com.service.freeblog_batch.job.step.NoSqlProcessStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FreeBlogBatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private com.service.freeblog_batch.job.step.RDBProcessStep RDBProcessStep;

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
