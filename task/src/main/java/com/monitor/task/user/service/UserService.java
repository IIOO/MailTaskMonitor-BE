package com.monitor.task.user.service;

import com.monitor.task.user.persistance.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);

    UserEntity findByUsername(String username);
}
