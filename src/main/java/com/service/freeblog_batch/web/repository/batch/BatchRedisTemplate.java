package com.service.freeblog_batch.web.repository.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchRedisTemplate {
    private final RedisTemplate redisTemplate;

    /**
     * 블로그 방문자 처리를 위한 redis 관리 메소드 작성
     */
}
