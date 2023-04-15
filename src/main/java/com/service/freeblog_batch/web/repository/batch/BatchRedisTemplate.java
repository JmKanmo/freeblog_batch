package com.service.freeblog_batch.web.repository.batch;

import com.service.freeblog_batch.web.domain.post.PostView;
import com.service.freeblog_batch.web.domain.visit.BlogVisitors;
import com.service.freeblog_batch.web.util.redis.RedisTemplateKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
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

    /**
     * 전 블로거 블로그 방문횟수 처리 (일일 방문자수, 전체 방문자수, 어제 방문자수)
     */
    public void updateVisitors() {
        List<String> list = (List<String>) redisTemplate.scan(ScanOptions.scanOptions().match(RedisTemplateKey.BLOG_VISITORS_KEY).build()).stream().collect(Collectors.toList());
        ValueOperations<String, BlogVisitors> blogVisitorsValueOperations = getBlogVisitorsOperation();

        for (String key : list) {
            BlogVisitors blogVisitors = blogVisitorsValueOperations.get(key);
            blogVisitors.update();
            blogVisitorsValueOperations.set(key, blogVisitors);
        }
    }

    public void deleteBlogVisitorCount(int blogId) {
        try {
            ValueOperations<String, BlogVisitors> valueOperations = getBlogVisitorsOperation();
            String key = String.format(RedisTemplateKey.BLOG_VISITORS_COUNT, blogId);

            if (valueOperations.get(key) != null) {
                getBlogVisitorsOperation().getAndDelete(key);
            }
        } catch (Exception e) {
            log.error("[BatchRedisTemplate:deleteBlogVisitorCount] error:{}", e);
        }
    }

    public void deletePostView(long blogId, long postId) {
        try {
            HashOperations<String, Long, PostView> hashOperations = getPostViewHashOperation();
            String key = String.format(RedisTemplateKey.POST_VIEWS, blogId);
            if (hashOperations.keys(key).contains(postId)) {
                hashOperations.delete(key, postId);
            }
        } catch (Exception e) {
            log.error("[BatchRedisTemplate:deletePostView] error:{}", e);
        }
    }

    public void deleteBlogPostViews(long blogId) {
        try {
            String postViewKey = String.format(RedisTemplateKey.POST_VIEWS, blogId);
            HashOperations<String, Long, PostView> postViewHashOperations = getPostViewHashOperation();

            if (postViewHashOperations.keys(postViewKey) != null) {
                postViewHashOperations.delete(postViewKey, postViewHashOperations.keys(postViewKey));
            }
        } catch (Exception e) {
            log.error("[BatchRedisTemplate:deleteBlogPostViews] error:{}", e);
        }
    }

    private HashOperations<String, Long, PostView> getPostViewHashOperation() {
        return redisTemplate.opsForHash();
    }

    private ValueOperations<String, BlogVisitors> getBlogVisitorsOperation() {
        return redisTemplate.opsForValue();
    }
}
