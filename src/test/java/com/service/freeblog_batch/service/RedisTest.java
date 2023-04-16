package com.service.freeblog_batch.service;

import com.service.freeblog_batch.web.domain.visit.BlogVisitors;
import com.service.freeblog_batch.web.util.json.JsonUtil;
import com.service.freeblog_batch.web.util.redis.RedisTemplateKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JsonUtil jsonUtil;

    @Test
    public void testRead() {
        List<String> list = (List<String>) redisTemplate.scan(ScanOptions.scanOptions().match(RedisTemplateKey.BLOG_VISITORS_KEY).build()).stream().collect(Collectors.toList());
        getBlogVisitorsOperation().get(list.get(0));
    }

    @Test
    public void writeTest() throws Exception {
        getBlogVisitorsOperation().set("freeblog-visitors"
                , jsonUtil.writeValueAsString(BlogVisitors.builder()
                        .todayViews(25)
                        .yesterdayViews(100)
                        .todayVisitors(255)
                        .yesterdayVisitors(5500)
                        .visitorSet(new HashSet<>())
                        .build()));
        BlogVisitors.builder().build();
    }

    @Test
    public void writeMultiTest() throws Exception {
        getBlogVisitorsOperation().set("freeblog-visitors-list"
                , jsonUtil.writeValueAsString(Arrays.asList(BlogVisitors.builder()
                                .todayViews(25)
                                .yesterdayViews(100)
                                .todayVisitors(255)
                                .yesterdayVisitors(5500)
                                .visitorSet(new HashSet<>())
                                .build(),
                        BlogVisitors.builder()
                                .todayViews(2432)
                                .yesterdayViews(543543)
                                .todayVisitors(54354343)
                                .yesterdayVisitors(354543)
                                .visitorSet(new HashSet<>())
                                .build(),
                        BlogVisitors.builder()
                                .todayViews(55)
                                .yesterdayViews(400)
                                .todayVisitors(355)
                                .yesterdayVisitors(5543200)
                                .visitorSet(new HashSet<>())
                                .build(),
                        BlogVisitors.builder()
                                .todayViews(235)
                                .yesterdayViews(1300)
                                .todayVisitors(2535)
                                .yesterdayVisitors(55500)
                                .visitorSet(new HashSet<>())
                                .build(),
                        BlogVisitors.builder()
                                .todayViews(255)
                                .yesterdayViews(1400)
                                .todayVisitors(2545)
                                .yesterdayVisitors(53500)
                                .visitorSet(new HashSet<>())
                                .build())));
        BlogVisitors.builder().build();
    }

    @Test
    public void readTest() throws Exception {
        BlogVisitors blogVisitors = jsonUtil.readClzValue(String.valueOf(getBlogVisitorsOperation().get("blog-visitors-count:1304824288")), BlogVisitors.class);
        Assertions.assertNotNull(blogVisitors);
    }

    @Test
    public void readMultiTest() throws Exception {
        List<BlogVisitors> blogVisitors = jsonUtil.readListValue(String.valueOf(getBlogVisitorsOperation().get("freeblog-visitors-list")), BlogVisitors.class);
        Assertions.assertNotNull(blogVisitors);
    }

    @Test
    public void deleteTest() throws Exception {
        getBlogVisitorsOperation().getAndDelete("freeblog-visitors");
    }

    @Test
    public void deleteMultiTest() throws Exception {
        getBlogVisitorsOperation().getAndDelete("freeblog-visitors-list");
    }

    private ValueOperations<String, Object> getBlogVisitorsOperation() {
        return redisTemplate.opsForValue();
    }
}
