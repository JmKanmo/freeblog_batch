package com.service.freeblog_batch.job;

import com.service.freeblog_batch.job.step.OldestFileProcessStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class OldestFileBatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private OldestFileProcessStep oldestFileProcessStep;

    @Bean
    public Job oldestFileJob() {
        return this.jobBuilderFactory.get("oldestFileJob")
                .start(oldestFileProcessStep.oldestFileStep())
                .build();
    }
}
