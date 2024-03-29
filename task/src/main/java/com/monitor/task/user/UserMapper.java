package com.monitor.task.user;

import com.monitor.task.user.dto.CreateUserDto;
import com.monitor.task.user.dto.UserDto;
import com.monitor.task.user.persistance.UserEntity;


public class UserMapper {
    public static UserEntity mapCreateUserToUserEntity(CreateUserDto dto) {
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public static UserDto mapUserEntityToUserDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .authorities(entity.getAuthorities())
                .build();
    }
}
