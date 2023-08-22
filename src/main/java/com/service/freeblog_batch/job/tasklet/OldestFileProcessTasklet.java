package com.service.freeblog_batch.job.tasklet;

import com.service.freeblog_batch.web.service.BatchSftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OldestFileProcessTasklet implements Tasklet {
    private final BatchSftpService batchSftpService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        batchSftpService.cleanOldestImageFile();
        return RepeatStatus.FINISHED;
    }
}
