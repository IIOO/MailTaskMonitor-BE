package com.monitor.task.security.service;

import com.monitor.task.security.persistance.AuthTokenEntity;
import com.monitor.task.user.persistance.UserEntity;

import java.util.List;
import java.util.UUID;

public interface AuthTokenService {
    void update(AuthTokenEntity token);

    void delete(AuthTokenEntity token);

    boolean isTokenExpired(AuthTokenEntity token);

    AuthTokenEntity create(UserEntity loggedUser);

    AuthTokenEntity findById(UUID id);

    List<AuthTokenEntity> findByUser(UserEntity user);
}
