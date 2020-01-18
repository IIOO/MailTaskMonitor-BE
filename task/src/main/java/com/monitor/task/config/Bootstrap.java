package com.monitor.task.config;

import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.repository.CompanyRepository;
import com.monitor.task.business.repository.MailAddressRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "bootstrap.enabled", havingValue = "true")
@Slf4j
public class Bootstrap implements InitializingBean {
    private static final String DEFAULT_PASSWORD = "password";
    private static Long taskId = 1L;

    private final UserService userService;

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final CompanyRepository companyRepository;
    private final MailAddressRepository mailAddressRepository;
    private final MailTaskRepository mailTaskRepository;


    @Override
    public void afterPropertiesSet() {
        log.info("Running bootstrap");
        initializeRoles();
        List<UserEntity> users = initializeUsers();

//        List<CompanyEntity> companies = createInitialCompanies(users);
//        // addresses by company
//        Map<Integer, List<MailAddressEntity>> companiesAddresses = createInitialMailAddresses(companies, 2);
//
//        List<MailTaskEntity> companyZeroTasks = createInitialMailTasksByAddressList(companiesAddresses.get(0), 5);
//        List<MailTaskEntity> companyOneTasks = createInitialMailTasksByAddressList(companiesAddresses.get(1), 2);
    }

    private void initializeRoles() {
        for (Role name : Role.values()) {
            RoleEntity roleEntity = RoleEntity.builder().role(name).build();
            roleRepository.save(roleEntity);
        }
        roleRepository.flush();
    }

    private List<UserEntity> initializeUsers() {
        createBootstrapAdmin();
        createBootstrapEmployee();

        UserEntity clientZero = createBootstrapClient("Zero");
        UserEntity clientOne = createBootstrapClient("One");
        UserEntity clientTwo = createBootstrapClient("Two");
        log.info("Users initialized");
        return Arrays.asList(clientZero, clientOne, clientTwo);
    }

    private List<CompanyEntity> createInitialCompanies(List<UserEntity> clients) {
        List<CompanyEntity> companies = new ArrayList<>();
        for (UserEntity client : clients) {
            CompanyEntity company = CompanyEntity.builder()
                    .id(client.getId())
                    .name(client.getUsername() + "POL")
                    .user(client)
                    .build();
            companies.add(companyRepository.saveAndFlush(company));
        }
        log.info("Companies initialized");
        return companies;
    }

    private Map<Integer, List<MailAddressEntity>> createInitialMailAddresses(List<CompanyEntity> companies, int numberOfAddresses) {
        HashMap<Integer, List<MailAddressEntity>> mailAddresses = new HashMap<>();

        for(int i = 0; i < companies.size(); i++) {
            List<MailAddressEntity> compAddresses = new ArrayList<>();

            for(int j = 0; j < numberOfAddresses; j++) {
                MailAddressEntity address = MailAddressEntity.builder()
                        .address(companies.get(i).getName().toLowerCase() + j + "@comp.com")
                        .company(companies.get(i))
                        .build();
                compAddresses.add(mailAddressRepository.save(address));
            }
            mailAddresses.put(i, compAddresses);
        }
        return mailAddresses;
    }

    private List<MailTaskEntity> createInitialMailTasksByAddressList(List<MailAddressEntity> addresses, int noOfTasksToCreate) {
        List<MailTaskEntity> tasks = new ArrayList<>();
        Random random = new Random();

        for (MailAddressEntity address : addresses) {
            for (int i = 0; i < noOfTasksToCreate; i++) {
                MailTaskEntity task = MailTaskEntity.builder()
                        .uid(taskId)
//                        .group()
                        .content("Mail " + taskId + " text content")
                        .from(address)
                        .subject("Order no. " + taskId)
                        .numberOfAttachments(random.nextInt(6))
                        .build();
                tasks.add(mailTaskRepository.saveAndFlush(task));
                taskId++;
            }
        }
        log.info(noOfTasksToCreate + " mail tasks initialized");
        return tasks;
    }

    @Transactional
    public UserEntity createBootstrapAdmin() {
        return userService.save(UserEntity.builder()
                .username("AUser")
                .password(DEFAULT_PASSWORD)
                .authorities(Collections.singletonList(roleService.find(Role.ADMIN)))
                .build());
    }

    @Transactional
    public UserEntity createBootstrapEmployee() {
        return userService.save(UserEntity.builder()
                .username("EUser")
                .password(DEFAULT_PASSWORD)
                .authorities(Collections.singletonList(roleService.find(Role.EMPLOYEE)))
                .build());
    }

    @Transactional
    public UserEntity createBootstrapClient(String username) {
        return userService.save(UserEntity.builder()
                .username(username)
                .password(DEFAULT_PASSWORD)
                .authorities(Collections.singletonList(roleService.find(Role.CLIENT)))
                .build());
    }
}
