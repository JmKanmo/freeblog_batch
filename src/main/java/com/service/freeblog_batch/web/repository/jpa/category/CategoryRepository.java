package com.service.freeblog_batch.web.repository.jpa.category;

import com.service.freeblog_batch.web.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
