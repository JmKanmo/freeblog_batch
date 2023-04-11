package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.repository.BatchRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchRedisService {
    private final BatchRedisTemplate batchRedisTemplate;


}
