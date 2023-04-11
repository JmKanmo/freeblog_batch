package com.service.freeblog_batch.web.repository.jpa.blog;

import com.service.freeblog_batch.web.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
