package com.service.freeblog_batch.web.service;

import com.service.freeblog_batch.web.domain.blog.Blog;
import com.service.freeblog_batch.web.domain.category.Category;
import com.service.freeblog_batch.web.domain.comment.Comment;
import com.service.freeblog_batch.web.domain.music.UserMusic;
import com.service.freeblog_batch.web.domain.music.UserMusicCategory;
import com.service.freeblog_batch.web.domain.post.Post;
import com.service.freeblog_batch.web.domain.tag.Tag;
import com.service.freeblog_batch.web.domain.user.User;
import com.service.freeblog_batch.web.domain.user.UserStatus;
import com.service.freeblog_batch.web.repository.batch.BatchJdbcTemplate;
import com.service.freeblog_batch.web.repository.jpa.blog.BlogRepository;
import com.service.freeblog_batch.web.repository.jpa.category.CategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.comment.CommentRepository;
import com.service.freeblog_batch.web.repository.jpa.music.UserMusicCategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.music.UserMusicRepository;
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
    private final UserMusicCategoryRepository userMusicCategoryRepository;
    private final UserMusicRepository userMusicRepository;

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
     * user(music/music-category): 사용자의 삭제 된 뮤직, 뮤직 요소가 없는 카테고리 삭제 처리
     */
    public void updateFreeBlogData() {
        try {
            // 삭제된 사용자 관련 데이터 처리
            updateDeletedUserRelatedData();

            // 삭제 된 게시글 관련 데이터 처리
            updateDeletedPostRelatedData();

            // 삭제된 카테고리 관련 데이터 처리
            updateDeletedCategoryRelatedData();

            // 삭제 된 사용자(뮤직,뮤직-카테고리) 관련 데이터 처리
            updateDeletedMusicRelatedData();
        } catch (Exception e) {
            log.error("[BatchJdbcService:processUser] error:{}", e);
        }
    }

    public void updateDeletedUserRelatedData() {
        try {
            List<Long> blogIds = updateWithdrawAndStopUsersRelatedData();
            updateDeletedBlogAndRelationEntities(blogIds);
        } catch (Exception e) {
            log.error("[BatchJdbcService:processDeletedUserRelatedData] error:{}", e);
        }
    }

    @Transactional
    public void updateDeletedPostRelatedData() {
        try {
            List<Post> postList = postRepository.findDeletedPostList();

            for (Post post : postList) {
                deleteCommentByPost(post);
                deleteTagByPost(post);
                // TODO delete Post thumbnail Image
                batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
            }
            postRepository.deleteAll(postList);
        } catch (Exception e) {
            log.error("[BatchJdbcService:processDeletedPostRelatedData] error:{}", e);
        }
    }

    @Transactional
    public void updateDeletedCategoryRelatedData() {
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
    public List<Long> updateWithdrawAndStopUsersRelatedData() {
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
    public void updateDeletedBlogAndRelationEntities(List<Long> blogIds) {
        List<Blog> blogList = blogRepository.findAllById(blogIds);

        for (Blog blog : blogList) {
            List<Post> postList = blog.getPostList();
            List<Category> categoryList = blog.getCategoryList();

            for (Post post : postList) {
                deleteCommentByPost(post);
                deleteTagByPost(post);
                // TODO delete Post thumbnail Image
                batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
            }
            postRepository.deleteAll(postList);
            categoryRepository.deleteAll(categoryList);
            deleteMusicInfoByBlog(blog);
        }
        blogRepository.deleteAll(blogList);
    }

    public void updateDeletedMusicRelatedData() {
        deleteUserMusicInfo();
        deleteMusicCategoryInfo();
    }

    @Transactional
    public void deleteMusicCategoryInfo() {
        try {
            List<UserMusicCategory> userMusicCategoryList = userMusicCategoryRepository.findDeletedUserMusicCategory();
            userMusicCategoryRepository.deleteAll(userMusicCategoryList);
        } catch (Exception e) {
            log.error("[BatchJdbcService:deleteMusicCategoryInfo] error:{}", e);
        }
    }

    @Transactional
    public void deleteUserMusicInfo() {
        try {
            List<UserMusic> userMusicList = userMusicRepository.findDeletedUserMusic();
            userMusicRepository.deleteAll(userMusicList);
        } catch (Exception e) {
            log.error("[BatchJdbcService:deleteMusicInfo] error:{}", e);
        }
    }

    private void deleteCommentByPost(Post post) {
        List<Comment> commentList = post.getCommentList();
        // TODO delete comment thumbnail image
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
            // TODO delete Post thumbnail Image
            batchSftpService.deleteImageFile(ConstUtil.SFTP_POST_IMAGE_HASH, post.getMetaKey());
        }
        postRepository.deleteAll(postList);
    }

    private void deleteMusicInfoByBlog(Blog blog) {
        List<UserMusicCategory> userMusicCategoryList = blog.getUserMusicCategoryList();

        for (UserMusicCategory userMusicCategory : userMusicCategoryList) {
            List<UserMusic> userMusicList = userMusicCategory.getUserMusicList();
            userMusicRepository.deleteAll(userMusicList);
        }
        userMusicCategoryRepository.deleteAll(userMusicCategoryList);
    }
}
