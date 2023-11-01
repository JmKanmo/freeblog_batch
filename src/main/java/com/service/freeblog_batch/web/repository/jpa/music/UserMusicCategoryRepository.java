package com.service.freeblog_batch.web.repository.jpa.music;

import com.service.freeblog_batch.web.domain.music.UserMusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMusicCategoryRepository extends JpaRepository<UserMusicCategory, Long> {
    @Query("select uc from UserMusicCategory uc where (select count(*) from UserMusic um where um.isDelete = false and um.userMusicCategory.id = uc.id) <=0")
    List<UserMusicCategory> findDeletedUserMusicCategory();
}
