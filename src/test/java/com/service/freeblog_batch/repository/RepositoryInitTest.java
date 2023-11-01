package com.service.freeblog_batch.repository;

import com.service.freeblog_batch.web.domain.music.UserMusic;
import com.service.freeblog_batch.web.domain.music.UserMusicCategory;
import com.service.freeblog_batch.web.domain.user.User;
import com.service.freeblog_batch.web.domain.user.UserStatus;
import com.service.freeblog_batch.web.repository.jpa.blog.BlogRepository;
import com.service.freeblog_batch.web.repository.jpa.category.CategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.comment.CommentRepository;
import com.service.freeblog_batch.web.repository.jpa.music.UserMusicCategoryRepository;
import com.service.freeblog_batch.web.repository.jpa.music.UserMusicRepository;
import com.service.freeblog_batch.web.repository.jpa.post.PostRepository;
import com.service.freeblog_batch.web.repository.jpa.tag.TagRepository;
import com.service.freeblog_batch.web.repository.jpa.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
public class RepositoryInitTest {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMusicRepository userMusicRepository;

    @Autowired
    private UserMusicCategoryRepository userMusicCategoryRepository;

    @Test
    public void init() {
        Assertions.assertNotNull(blogRepository);
        Assertions.assertNotNull(categoryRepository);
        Assertions.assertNotNull(commentRepository);
        Assertions.assertNotNull(postRepository);
        Assertions.assertNotNull(tagRepository);
        Assertions.assertNotNull(userRepository);
    }

    @Test
    void withdrawUserTest() {
        List<User> userList = userRepository.findWithdrawAndStopUsers(new UserStatus[]{UserStatus.NOT_AUTH});
        Assertions.assertNotNull(userList);
    }

    @Test
    @Transactional(readOnly = true)
    void userMusicTest() {
        List<UserMusic> userMusicList = userMusicRepository.findDeletedUserMusic();
        Assertions.assertNotNull(userMusicList);
    }

    @Test
    @Transactional(readOnly = true)
    void userMusicCategoryTest() {
        List<UserMusicCategory> userMusicCategoryList = userMusicCategoryRepository.findDeletedUserMusicCategory();
        Assertions.assertNotNull(userMusicCategoryList);
    }
}
