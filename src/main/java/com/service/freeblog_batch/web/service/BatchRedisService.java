package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.repository.batch.BatchRedisTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchRedisService {
    private final BatchRedisTemplate batchRedisTemplate;

    public void processBlogVisitUpdate() {
        try {
            batchRedisTemplate.updateVisitors();
        } catch (Exception e) {
            log.error("[BatchRedisService:processBlogVisitUpdate] error:{}", e);
        }
    }
}
