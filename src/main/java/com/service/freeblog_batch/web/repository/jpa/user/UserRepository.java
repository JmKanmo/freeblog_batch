package com.service.freeblog_batch.web.repository.jpa.user;

import com.service.freeblog_batch.web.domain.user.User;
import com.service.freeblog_batch.web.domain.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.status in (:status)")
    List<User> findWithdrawAndStopUsers(@Param("status") UserStatus[] status);
}
