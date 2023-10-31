package com.service.freeblog_batch.web.domain.music;

import com.service.freeblog_batch.web.util.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_music",
        indexes = {
                @Index(name = "hashcode_idx_id", columnList = "hashCode")
        })
@ToString(exclude = "userMusicCategory")
public class UserMusic extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_music_id")
    private Long id;

    private String name;

    private String artist;

    private String url;

    private String cover;

    private String lrc;

    private boolean isDelete;

    private int hashCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_category_id")
    private UserMusicCategory userMusicCategory;
}

