package com.monitor.task.user.dto;

import com.monitor.task.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String name;
    private String surname;
    private String mail;
    private String password;
    private Role role;
}
