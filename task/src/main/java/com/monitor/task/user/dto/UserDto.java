package com.monitor.task.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String mail;
    private List<GrantedAuthority> authorities;
}
