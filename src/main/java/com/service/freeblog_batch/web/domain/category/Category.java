package com.service.freeblog_batch.web.domain.category;

import com.service.freeblog_batch.web.domain.blog.Blog;
import com.service.freeblog_batch.web.domain.post.Post;
import com.service.freeblog_batch.web.util.domain.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "blog")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private Long parentId;

    @Column(length = 25)
    private String name;

    private Long seq;

    private boolean isDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "category")
    private List<Post> postList;
}
