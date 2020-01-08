package com.monitor.task.user.repository;

import com.monitor.task.user.persistance.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findUserEntityByUsername(String username);
}
