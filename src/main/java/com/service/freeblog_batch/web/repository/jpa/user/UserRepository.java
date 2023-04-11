package com.service.freeblog_batch.web.repository.jpa.user;

import com.service.freeblog_batch.web.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
