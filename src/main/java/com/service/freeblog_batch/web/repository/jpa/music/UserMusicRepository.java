package com.service.freeblog_batch.web.repository.jpa.music;

import com.service.freeblog_batch.web.domain.music.UserMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMusicRepository extends JpaRepository<UserMusic, Long> {
}
