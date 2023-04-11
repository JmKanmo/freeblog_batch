package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.repository.batch.BatchJdbcTemplate;
import com.service.freeblog_batch.web.repository.jpa.blog.BlogRepository;
import com.service.freeblog_batch.web.repository.jpa.category.CategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.comment.CommentRepository;
import com.service.freeblog_batch.web.repository.jpa.post.PostRepository;
import com.service.freeblog_batch.web.repository.jpa.tag.TagRepository;
import com.service.freeblog_batch.web.repository.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BatchJdbcService {
    private final BatchJdbcTemplate batchJdbcTemplate;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;


    /**
     * TODO 서비스 별로 메소드 작성해서 일괄처리
     * RDB 데이터 처리
     * user : 정지, 탈퇴 회원 처리
     * blog: 삭제 된 블로그 처리
     * category: 삭제 된 블로그의 카테고리 처리
     * post: 삭제 된 포스트 처리
     * tag: (삭제 된 포스트의 태그 처리)
     * comment: (삭제 된 게시글의 댓글 처리)
     */
    public void processData() {
        try {

        } catch (Exception e) {
            log.error("[BatchJdbcService:processUser] error:{}", e);
        }
    }
}
