package com.service.freeblog_batch.job.step;

import com.service.freeblog_batch.job.tasklet.NoSqlProcessTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoSqlProcessStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private NoSqlProcessTasklet noSqlProcessTasklet;

    public Step noSqlCleanStep() {
        return this.stepBuilderFactory.get("noSqlCleanStep")
                .tasklet(noSqlProcessTasklet).build();
    }
}
