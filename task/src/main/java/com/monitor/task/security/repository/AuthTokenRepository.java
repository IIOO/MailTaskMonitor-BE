package com.monitor.task.security.repository;

import com.monitor.task.security.persistance.AuthTokenEntity;
import com.monitor.task.user.persistance.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, Long> {
    Optional<List<AuthTokenEntity>> findByUser(UserEntity userEntity);
}
