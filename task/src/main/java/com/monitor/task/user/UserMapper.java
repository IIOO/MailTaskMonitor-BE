package com.monitor.task.user;

import com.monitor.task.user.dto.CreateUserDto;
import com.monitor.task.user.dto.UserDto;
import com.monitor.task.user.persistance.UserEntity;


public class UserMapper {
    public static UserEntity mapCreateUserToUserEntity(CreateUserDto dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .mail(dto.getMail())
                .password(dto.getPassword())
                .build();
    }

    public static UserDto mapFromUserEntity(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .mail(entity.getMail())
                .role(entity.getRole())
                .build();
    }
}
