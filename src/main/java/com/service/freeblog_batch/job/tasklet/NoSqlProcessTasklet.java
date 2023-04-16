package com.service.freeblog_batch.job.tasklet;

import com.service.freeblog_batch.web.service.BatchRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NoSqlProcessTasklet implements Tasklet {
    private final BatchRedisService batchRedisService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        batchRedisService.processBlogVisitUpdate();
        return RepeatStatus.FINISHED;
    }
}
