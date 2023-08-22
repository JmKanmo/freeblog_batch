package com.service.freeblog_batch.job.scheduler;

import com.service.freeblog_batch.job.FreeBlogBatchJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BatchJobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private FreeBlogBatchJob freeBlogBatchJob;

    /**
     * TODO RDS, NoSql 데이터 정리 스케줄링
     */
    @Scheduled(cron = "0 59 23 * * *") // 매일 23시 59분에 실행
    public void freeBlogBatchJobScheduler() {
        try {
            log.info("[BatchJobScheduler:freeBlogBatchJobScheduler] freeblog batch scheduler done");
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);
            jobLauncher.run(freeBlogBatchJob.processJob(), jobParameters);
        } catch (Exception e) {
            log.error("[BatchJobScheduler:freeBlogBatchJobScheduler] scheduler error =>", e);
        }
    }

    /**
     * TODO Sftp <-> 파일 서버, 오래된 미참조 파일 삭제 스케쥴링
     */
    @Scheduled(cron = "0 44 4 1 1 *") // 매일 23시 59분에 실행
    public void freeBlogBatchJobScheduler1() {
        try {
            log.info("[BatchJobScheduler:freeBlogBatchJobScheduler1] freeblog batch scheduler done");
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);
            jobLauncher.run(freeBlogBatchJob.processJob(), jobParameters);
        } catch (Exception e) {
            log.error("[BatchJobScheduler:freeBlogBatchJobScheduler] scheduler error =>", e);
        }
    }
}
