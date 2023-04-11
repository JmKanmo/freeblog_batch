package com.service.freeblog_batch.web.repository.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;


}
