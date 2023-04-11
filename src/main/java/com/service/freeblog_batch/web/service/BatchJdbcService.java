package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.repository.BatchJdbcTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchJdbcService {
    private final BatchJdbcTemplate batchJdbcTemplate;


}
