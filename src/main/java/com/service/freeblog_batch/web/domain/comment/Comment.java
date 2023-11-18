package com.service.freeblog_batch.web.domain.comment;

import com.service.freeblog_batch.web.domain.post.Post;
import com.service.freeblog_batch.web.util.domain.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "post")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long parentId;

    private boolean isDelete;

    private String href;

    @Column(length = 2000)
    private String comment;

    private String commentImage;

    private boolean secret;

    private boolean anonymous;

    private String metaKey;

    @Embedded
    private CommentUser commentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
