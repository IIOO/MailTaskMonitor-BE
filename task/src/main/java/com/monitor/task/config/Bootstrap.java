package com.monitor.task.config;

import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;
import com.monitor.task.business.repository.CompanyRepository;
import com.monitor.task.business.repository.MailAddressRepository;
import com.monitor.task.business.repository.MailTaskGroupRepository;
import com.monitor.task.business.repository.MailTaskRepository;
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

import java.util.*;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "bootstrap.enabled", havingValue = "true")
@Slf4j
public class Bootstrap implements InitializingBean {
    private static final String DEFAULT_PASSWORD = "password";

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final RoleService roleService;


    private final CompanyRepository companyRepository;

    private final MailAddressRepository mailAddressRepository;

    private final MailTaskRepository mailTaskRepository;

    private final MailTaskGroupRepository mailTaskGroupRepository;


    @Override
    public void afterPropertiesSet() {
        log.info("Running bootstrap");
        initializeRoles();
        initializeUsers();

//        createInitialMailTask(1, 2, createInitialMailAddress("Test"));
//        createInitialMailTaskGroup(2, 3);
    }

    private void initializeRoles() {
        for (Role name : Role.values()) {
            RoleEntity roleEntity = RoleEntity.builder().role(name.toString()).build();
            roleRepository.save(roleEntity);
        }
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

    private MailTaskEntity createInitialMailTask(int messageNumber, int numberOfAttachments, MailAddressEntity mailAddress) {
        MailTaskEntity task = MailTaskEntity.builder()
                .messageNumber(messageNumber)
                .content("Mail text content")
                .from(mailAddress)
                .subject("Order no. " + messageNumber)
                .numberOfAttachments(numberOfAttachments)
                .build();
        mailTaskRepository.saveAndFlush(task);
        return task;
    }

    private MailAddressEntity createInitialMailAddress(String companyName) {
        MailAddressEntity address = MailAddressEntity.builder()
                .address(Optional.ofNullable(companyName).map(name -> name + "@comp.com").orElse("random@gmail.com"))
                .company(Optional.ofNullable(companyName).map(this::createInitialCompany).orElse(null))
                .build();
        mailAddressRepository.saveAndFlush(address);
        return address;
    }

    private CompanyEntity createInitialCompany(String companyName) {
        CompanyEntity company = CompanyEntity.builder()
                .name(companyName + "POL")
                .build();
        companyRepository.saveAndFlush(company);
        return company;
    }

    private MailTaskGroupEntity createInitialMailTaskGroup(int initialMessageNumber, int groupSize) {
        Set<MailTaskEntity> tasks = new HashSet<>();
        for (int i = initialMessageNumber; i < groupSize + initialMessageNumber; i++) {
            tasks.add(createInitialMailTask(i, 0, createInitialMailAddress(null)));
        }
        MailTaskGroupEntity group = MailTaskGroupEntity.builder()
                .name("Group 1")
                .build();
        mailTaskGroupRepository.saveAndFlush(group);
        return group;
    }
}
