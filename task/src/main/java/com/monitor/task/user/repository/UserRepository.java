package com.monitor.task.user.repository;

import com.monitor.task.user.persistance.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityById(Long id);
    Optional<UserEntity> findUserEntityByUsername(String username);
}
