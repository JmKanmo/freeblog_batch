package com.service.freeblog_batch.web.repository.jpa.post;

import com.service.freeblog_batch.web.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
