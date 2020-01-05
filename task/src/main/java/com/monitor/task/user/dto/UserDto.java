package com.monitor.task.user.dto;

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
    private String password;
    private String role;
}
