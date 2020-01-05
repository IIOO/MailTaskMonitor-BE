package com.monitor.task.user.service;

import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.repository.RoleRepository;
import com.monitor.task.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(roleRepository.findByName(user.getRole().getName()).orElse(null));
        return userRepository.save(user);
    }

    @Override
    public UserEntity findBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }
}
