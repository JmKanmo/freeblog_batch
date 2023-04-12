package com.service.freeblog_batch.web.repository.jpa.category;

import com.service.freeblog_batch.web.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.isDelete = true")
    List<Category> findDeletedCategoryList();
}
