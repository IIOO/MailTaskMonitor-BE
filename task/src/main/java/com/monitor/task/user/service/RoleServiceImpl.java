package com.monitor.task.user.service;

import com.monitor.task.user.Role;
import com.monitor.task.user.persistance.RoleEntity;
import com.monitor.task.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity find(Role role) {
        return roleRepository.findRoleEntityByRole(role.name());
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }
}
