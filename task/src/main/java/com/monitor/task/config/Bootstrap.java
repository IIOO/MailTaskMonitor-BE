package com.monitor.task.config;

import com.monitor.task.user.Role;
import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "bootstrap.enabled", havingValue = "true")
@Slf4j
public class Bootstrap implements InitializingBean {
    private static final String DEFAULT_PASSWORD = "password";

    private final UserService userService;

    @Override
    public void afterPropertiesSet() {
        log.info("Running bootstrap");
        userService.save(createBootstrapAdmin());
        userService.save(createBootstrapUser());
    }

    private UserEntity createBootstrapAdmin() {
        return UserEntity.builder()
                .name("Admin")
                .surname("ASurname")
                .mail("admin.surname@test.pl")
                .password(DEFAULT_PASSWORD)
                .role(Role.ADMIN)
                .build();
    }

    private UserEntity createBootstrapUser() {
        return UserEntity.builder()
                .name("User")
                .surname("USurname")
                .mail("user.surname@test.pl")
                .password(DEFAULT_PASSWORD)
                .role(Role.USER)
                .build();
    }
}
