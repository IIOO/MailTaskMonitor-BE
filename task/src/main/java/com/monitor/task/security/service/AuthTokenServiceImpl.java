package com.monitor.task.security.service;

import com.monitor.task.security.persistance.AuthTokenEntity;
import com.monitor.task.security.repository.AuthTokenRepository;
import com.monitor.task.user.persistance.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    @Override
    public void update(AuthTokenEntity token) {
        token.setLastAccessTime(LocalDateTime.now());
        authTokenRepository.save(token);
    }

    @Override
    public void delete(AuthTokenEntity token) {
        authTokenRepository.delete(token);
    }

    @Override
    public boolean isTokenExpired(AuthTokenEntity token) {
//       TODO add logic
        return false;
    }

    @Override
    public AuthTokenEntity create(UserEntity loggedUser) {
        AuthTokenEntity token = AuthTokenEntity.builder()
                .user(loggedUser)
                .build();
        authTokenRepository.saveAndFlush(token);
        return token;
    }

    @Override
    public AuthTokenEntity findById(Long id) {
        return authTokenRepository.findById(id).orElse(null);
    }

    @Override
    public List<AuthTokenEntity> findByUser(UserEntity user) {
        return authTokenRepository.findByUser(user).orElse(new ArrayList<>());
    }
}
