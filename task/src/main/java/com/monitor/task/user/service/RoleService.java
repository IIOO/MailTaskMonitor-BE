package com.monitor.task.user.service;

import com.monitor.task.user.Role;
import com.monitor.task.user.persistance.RoleEntity;

import java.util.List;

public interface RoleService {
    RoleEntity find(Role role);

    List<RoleEntity> findAll();
}
