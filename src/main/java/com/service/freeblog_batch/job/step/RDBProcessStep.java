package com.service.freeblog_batch.job.step;

import com.service.freeblog_batch.job.tasklet.RDBProcessTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RDBProcessStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RDBProcessTasklet rdbProcessTasklet;

    public Step dbCleanStep() {
        return this.stepBuilderFactory.get("dbCleanStep")
                .tasklet(rdbProcessTasklet).build();
    }
}
