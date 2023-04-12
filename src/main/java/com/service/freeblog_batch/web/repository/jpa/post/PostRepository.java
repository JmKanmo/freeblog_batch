package com.service.freeblog_batch.web.repository.jpa.post;

import com.service.freeblog_batch.web.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.isDelete = true")
    List<Post> findDeletedPostList();
}
