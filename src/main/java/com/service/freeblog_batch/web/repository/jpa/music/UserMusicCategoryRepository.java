package com.service.freeblog_batch.web.repository.jpa.music;

import com.service.freeblog_batch.web.domain.music.UserMusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMusicCategoryRepository extends JpaRepository<UserMusicCategory, Long> {
}
