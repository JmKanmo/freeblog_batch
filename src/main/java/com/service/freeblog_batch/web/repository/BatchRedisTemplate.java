package com.service.freeblog_batch.web.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchRedisTemplate {
    private final RedisTemplate redisTemplate;
}
