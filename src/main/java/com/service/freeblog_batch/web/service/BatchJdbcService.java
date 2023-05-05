package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.domain.blog.Blog;
import com.service.freeblog_batch.web.domain.category.Category;
import com.service.freeblog_batch.web.domain.comment.Comment;
import com.service.freeblog_batch.web.domain.post.Post;
import com.service.freeblog_batch.web.domain.tag.Tag;
import com.service.freeblog_batch.web.domain.user.User;
import com.service.freeblog_batch.web.domain.user.UserStatus;
import com.service.freeblog_batch.web.repository.batch.BatchJdbcTemplate;
import com.service.freeblog_batch.web.repository.jpa.blog.BlogRepository;
import com.service.freeblog_batch.web.repository.jpa.category.CategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.comment.CommentRepository;
import com.service.freeblog_batch.web.repository.jpa.post.PostRepository;
import com.service.freeblog_batch.web.repository.jpa.tag.TagRepository;
import com.service.freeblog_batch.web.repository.jpa.user.UserRepository;
import com.service.freeblog_batch.web.util.ConstUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchJdbcService {
    private final BatchJdbcTemplate batchJdbcTemplate;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    private final BatchSftpService batchSftpService;

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
    public void updateFreeBlogData() {
        try {
            // 삭제된 사용자 관련 데이터 처리
            updateDeletedUserRelatedData();

            // 삭제 된 게시글 관련 데이터 처리
            updateDeletedPostRelatedData();

            // 삭제된 카테고리 관련 데이터 처리
            updateDeletedCategoryRelatedData();
        } catch (Exception e) {
            log.error("[BatchJdbcService:processUser] error:{}", e);
        }
    }

    void updateDeletedUserRelatedData() {
        try {
            List<Long> blogIds = updateWithdrawAndStopUsersRelatedData();
            updateDeletedBlogAndRelationEntities(blogIds);
        } catch (Exception e) {
            log.error("[BatchJdbcService:processDeletedUserRelatedData] error:{}", e);
        }
    }

    @Transactional
    void updateDeletedPostRelatedData() {
        try {
            List<Post> postList = postRepository.findDeletedPostList();

            for (Post post : postList) {
                deleteCommentByPost(post);
                deleteTagByPost(post);
                batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
            }
            postRepository.deleteAll(postList);
        } catch (Exception e) {
            log.error("[BatchJdbcService:processDeletedPostRelatedData] error:{}", e);
        }
    }

    @Transactional
    void updateDeletedCategoryRelatedData() {
        try {
            List<Category> categoryList = categoryRepository.findDeletedCategoryList();

            for (Category category : categoryList) {
                deletePostByCategory(category);
            }
            categoryRepository.deleteAll(categoryList);
        } catch (Exception e) {
            log.error("[BatchJdbcService:processDeletedCategoryRelatedData] error:{}", e);
        }
    }

    @Transactional
    List<Long> updateWithdrawAndStopUsersRelatedData() {
        List<User> users = userRepository.findWithdrawAndStopUsers(new UserStatus[]{UserStatus.WITHDRAW, UserStatus.STOP});
        // TODO delete user thumbnail image
        List<Long> blogIds = users.stream().map(user -> {
            Blog blog = user.getBlog();
            return blog.getId();
        }).collect(Collectors.toList());
        userRepository.deleteAll(users);
        return blogIds;
    }

    @Transactional
    void updateDeletedBlogAndRelationEntities(List<Long> blogIds) {
        List<Blog> blogList = blogRepository.findAllById(blogIds);

        for (Blog blog : blogList) {
            List<Post> postList = blog.getPostList();
            List<Category> categoryList = blog.getCategoryList();

            for (Post post : postList) {
                deleteCommentByPost(post);
                deleteTagByPost(post);
                batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
            }
            postRepository.deleteAll(postList);
            categoryRepository.deleteAll(categoryList);
        }
        blogRepository.deleteAll(blogList);
    }

    private void deleteCommentByPost(Post post) {
        List<Comment> commentList = post.getCommentList();
        // TODO delete comment image
        commentRepository.deleteAll(commentList);
    }

    private void deleteTagByPost(Post post) {
        List<Tag> tagList = post.getTagList();
        tagRepository.deleteAll(tagList);
    }

    private void deletePostByCategory(Category category) {
        List<Post> postList = category.getPostList();

        for (Post post : postList) {
            deleteCommentByPost(post);
            deleteTagByPost(post);
            batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
        }
        postRepository.deleteAll(postList);
    }
}
