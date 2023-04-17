package com.service.freeblog_batch.web.repository.batch;

import com.service.freeblog_batch.web.domain.visit.BlogVisitors;
import com.service.freeblog_batch.web.util.json.JsonUtil;
import com.service.freeblog_batch.web.util.redis.RedisTemplateKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BatchRedisTemplate {
    private final RedisTemplate redisTemplate;

    private final JsonUtil jsonUtil;

    /**
     * 전 블로거 블로그 방문횟수 처리 (일일 방문자수, 전체 방문자수, 어제 방문자수)
     */
    public void updateVisitors() {
        List<String> list = (List<String>) redisTemplate.scan(ScanOptions.scanOptions().match(RedisTemplateKey.BLOG_VISITORS_KEY).build()).stream().collect(Collectors.toList());
        ValueOperations<String, Object> blogVisitorsValueOperations = getBlogVisitorsOperation();

        for (String key : list) {
            try {
                BlogVisitors blogVisitors = jsonUtil.readClzValue(String.valueOf(blogVisitorsValueOperations.get(key)), BlogVisitors.class);
                blogVisitors.update();
                writeBlogVisitorsOperation(key, blogVisitors);
            } catch (Exception e) {
                log.error("[BatchRedisTemplate:updateVisitors] error:{}", e);
            }
        }
    }

    private void writeBlogVisitorsOperation(Object key, Object value) throws Exception {
        getBlogVisitorsOperation().set(String.valueOf(key), jsonUtil.writeValueAsString(value));
    }

    private ValueOperations<String, Object> getBlogVisitorsOperation() {
        return redisTemplate.opsForValue();
    }
}
