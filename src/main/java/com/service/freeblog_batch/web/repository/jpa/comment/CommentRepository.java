package com.service.freeblog_batch.web.repository.jpa.comment;

import com.service.freeblog_batch.web.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
