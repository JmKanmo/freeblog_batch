package com.service.freeblog_batch.job.tasklet;

import com.service.freeblog_batch.web.service.BatchJdbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RDBProcessTasklet implements Tasklet {
    private final BatchJdbcService batchJdbcService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        batchJdbcService.processData();
        return RepeatStatus.FINISHED;
    }
}
