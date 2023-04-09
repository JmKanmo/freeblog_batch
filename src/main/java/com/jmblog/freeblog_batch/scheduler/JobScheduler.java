package com.jmblog.freeblog_batch.scheduler;

import com.jmblog.freeblog_batch.job.FreeBlogBatchJob;
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
public class JobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private FreeBlogBatchJob freeBlogBatchJob;

    @Scheduled(cron = "5 * * * * *")
    public void freeBlogBatchJobScheduler() {
        try {
            Map<String, JobParameter> confMap = new HashMap<>();
            confMap.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters jobParameters = new JobParameters(confMap);
            jobLauncher.run(freeBlogBatchJob.processJob(), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
