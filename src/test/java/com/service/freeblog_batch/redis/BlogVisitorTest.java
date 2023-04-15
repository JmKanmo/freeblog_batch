package com.service.freeblog_batch.redis;

import com.service.freeblog_batch.web.util.redis.RedisTemplateKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class BlogVisitorTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void initAndLoopTest() {
        List<?> list = (List<?>) redisTemplate.scan(ScanOptions.scanOptions().match(RedisTemplateKey.BLOG_VISITORS_KEY).build()).stream().collect(Collectors.toList());

        Assertions.assertNotNull(list);

        for (Object obj : list) {
            System.out.println(obj);
        }
    }
}
