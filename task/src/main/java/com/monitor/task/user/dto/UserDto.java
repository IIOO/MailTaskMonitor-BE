package com.monitor.task.user.dto;

import com.monitor.task.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String name;
    private String surname;
    private String mail;
    private Role role;
}
