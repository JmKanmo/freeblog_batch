package com.service.freeblog_batch.web.domain.blog;


import com.service.freeblog_batch.web.domain.category.Category;
import com.service.freeblog_batch.web.domain.music.UserMusicCategory;
import com.service.freeblog_batch.web.domain.post.Post;
import com.service.freeblog_batch.web.domain.user.User;
import com.service.freeblog_batch.web.util.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Blog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    private String name;

    private boolean isDelete;

    @Lob
    private String intro;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "blog")
    private User user;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog")
    private List<Category> categoryList;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog")
    private List<Post> postList;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog")
    private List<UserMusicCategory> userMusicCategoryList;
}
