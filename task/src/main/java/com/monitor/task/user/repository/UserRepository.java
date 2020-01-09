package com.monitor.task.user.repository;

import com.monitor.task.user.persistance.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByUsername(String username);
}
