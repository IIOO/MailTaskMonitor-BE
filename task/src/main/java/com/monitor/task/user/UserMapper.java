package com.monitor.task.user;

import com.monitor.task.user.dto.CreateUserDto;
import com.monitor.task.user.dto.UserDto;
import com.monitor.task.user.persistance.UserEntity;


public class UserMapper {
    public static UserEntity mapCreateUserToUserEntity(CreateUserDto dto) {
        return UserEntity.builder()
                .username(dto.getName())
                .mail(dto.getMail())
                .password(dto.getPassword())
                .build();
    }

    public static UserDto mapUserEntityToUserDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .mail(entity.getMail())
                .authorities(entity.getAuthorities())
                .build();
    }
}
