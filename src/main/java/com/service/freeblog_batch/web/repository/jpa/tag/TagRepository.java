package com.service.freeblog_batch.web.repository.jpa.tag;

import com.service.freeblog_batch.web.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
