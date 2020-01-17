package com.monitor.task.business;

import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.user.UserMapper;

import java.util.Optional;

public class MailTaskMapper {
    public static TaskPreviewDto mapMailTaskEntityToTaskPreviewDto(MailTaskEntity entity) {
        return TaskPreviewDto.builder()
                .uid(entity.getUid())
                .orderNo(entity.getOrderNo())
                .from(entity.getFrom().getAddress())
                .subject(entity.getSubject())
                .status(entity.getStatus())
                .build();
    }

    public static TaskDto mapMailTaskEntityToTaskDto(MailTaskEntity entity) {
        return TaskDto.builder()
                .uid(entity.getUid())
                .orderNo(entity.getOrderNo())
                .from(entity.getFrom().getAddress())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .status(entity.getStatus())
                .numberOfAttachments(entity.getNumberOfAttachments())
                .assignedUser(Optional.ofNullable(entity.getUser()).map(UserMapper::mapUserEntityToUserDto).orElse(null))
                .receivedDate(entity.getReceivedDate())
                .build();
    }

    public static MailTaskEntity mapTaskDtoToMailTaskEntity(TaskDto dto, MailAddressEntity address) {
        return MailTaskEntity.builder()
                .uid(dto.getUid())
                .orderNo(dto.getOrderNo())
                .from(address)
                .subject(dto.getSubject())
                .content(dto.getContent())
                .numberOfAttachments(dto.getNumberOfAttachments())
                .receivedDate(dto.getReceivedDate())
                .build();
    }
}
