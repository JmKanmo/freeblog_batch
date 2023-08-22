package com.service.freeblog_batch.job.step;

import com.service.freeblog_batch.job.tasklet.OldestFileProcessTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OldestFileProcessStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private OldestFileProcessTasklet oldestFileProcessTasklet;

    public Step oldestFileStep() {
        return this.stepBuilderFactory.get("oldestFileStep")
                .tasklet(oldestFileProcessTasklet).build();
    }
}
