package com.service.freeblog_batch.web.repository.jpa.music;

import com.service.freeblog_batch.web.domain.music.UserMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMusicRepository extends JpaRepository<UserMusic, Long> {
    @Query("select u from UserMusic u where u.isDelete = true")
    List<UserMusic> findDeletedUserMusic();
}
