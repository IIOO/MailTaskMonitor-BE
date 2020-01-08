package com.monitor.task.config;

import com.monitor.task.user.Role;
import com.monitor.task.user.persistance.RoleEntity;
import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.repository.RoleRepository;
import com.monitor.task.user.service.RoleService;
import com.monitor.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "bootstrap.enabled", havingValue = "true")
@Slf4j
public class Bootstrap implements InitializingBean {
    private static final String DEFAULT_PASSWORD = "password";

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final RoleService roleService;

    @Override
    public void afterPropertiesSet() {
        log.info("Running bootstrap");
        initializeRoles();
        initializeUsers();
    }

    private void initializeRoles() {
        for (Role name : Role.values()) {
            RoleEntity roleEntity = RoleEntity.builder().role(name.toString()).build();
            roleRepository.save(roleEntity);
        }
//        TODO what for?
        roleRepository.flush();
    }

    private void initializeUsers() {
        userService.save(createBootstrapAdmin());
        userService.save(createBootstrapUser());
    }

    private UserEntity createBootstrapAdmin() {
        return UserEntity.builder()
                .username("ASurname")
                .mail("admin.surname@test.pl")
                .password(DEFAULT_PASSWORD)
                .authorities(Collections.singletonList(roleService.find(Role.ADMIN)))
                .build();
    }

    private UserEntity createBootstrapUser() {
        return UserEntity.builder()
                .username("USurname")
                .mail("user.surname@test.pl")
                .password(DEFAULT_PASSWORD)
                .authorities(Collections.singletonList(roleService.find(Role.USER)))
                .build();
    }
}
