package com.service.freeblog_batch.web.domain.user;

import com.service.freeblog_batch.web.domain.blog.Blog;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "blog")
@Table(name = "user", indexes = {
        @Index(name = "user_idx_email", columnList = "email"),
        @Index(name = "user_idx_nickname", columnList = "nickname")
})
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String nickname;

    private String greetings;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Embedded
    private SocialAddress socialAddress;

    private boolean isAuth;

    private LocalDateTime emailAuthTime;
    private LocalDateTime passwordUpdateTime;
    private LocalDateTime withdrawTime;
}
