package com.monitor.task.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String mail;
    private List<GrantedAuthority> authorities;
}
