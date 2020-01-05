package com.monitor.task.user;

import com.monitor.task.user.dto.UserDto;
import com.monitor.task.user.persistance.RoleEntity;
import com.monitor.task.user.persistance.UserEntity;

import java.util.Optional;

public class UserMapper {
    public static UserEntity mapToUserEntity(UserDto dto) {
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
                .role(Optional.ofNullable(entity.getRole()).map(RoleEntity::getName).orElse(null))
                .build();
    }
}
