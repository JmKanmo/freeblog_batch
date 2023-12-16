package com.service.freeblog_batch.config.batch;

import com.service.freeblog_batch.config.yaml.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yml")
@Data
public class BatchConfig {
    @Value("${util-config.app_config.batch_scheduled_freeblog_cron_job}")
    private String batchScheduledFreeblogCronJob;

    @Value("${util-config.app_config.batch_scheduled_old_file_clean_job}")
    private String batchScheduledOldFileCleanCronJob;

    @Value("${util-config.app_config.batch_region_time}")
    private String batchRegionTime;

    @Value("${util-config.batch_config.old_file_clean_period}")
    private Long oldFileCleanPeriod;
}
