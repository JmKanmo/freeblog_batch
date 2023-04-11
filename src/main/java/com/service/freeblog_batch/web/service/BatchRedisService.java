package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.repository.batch.BatchRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchRedisService {
    private final BatchRedisTemplate batchRedisTemplate;

    /**
     * 블로그 방문자 처리
     */
    public void processBlogVisit() {
        // TODO
    }
}
