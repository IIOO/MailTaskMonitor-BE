package com.monitor.task.user.dto;

import com.monitor.task.user.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String password;
    private Role role;
    private String companyName;
    private List<String> mailAddresses;
}
