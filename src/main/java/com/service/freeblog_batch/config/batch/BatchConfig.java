package com.service.freeblog_batch.config.batch;

import com.service.freeblog_batch.config.yaml.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:application-util.yml", factory = YamlPropertySourceFactory.class)
@Data
public class BatchConfig {
    @Value("${util-config.batch_config.old_file_clean_period}")
    private Long oldFileCleanPeriod;
}
