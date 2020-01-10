package com.monitor.task.business;

import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.user.UserMapper;

public class MailTaskMapper {
    public static TaskPreviewDto mapMailTaskEntityToTaskPreviewDto(MailTaskEntity entity) {
        return TaskPreviewDto.builder()
                .id(entity.getMessageNumber())
                .from(entity.getFrom().getAddress())
                .subject(entity.getSubject())
                .build();
    }

    public static TaskDto mapMailTaskEntityToTaskDto(MailTaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getMessageNumber())
                .from(entity.getFrom().getAddress())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .numberOfAttachments(entity.getNumberOfAttachments())
                .assignedUser(UserMapper.mapUserEntityToUserDto(entity.getUser()))
                .build();
    }

    public static MailTaskEntity mapTaskDtoToMailTaskEntity(TaskDto dto) {
        return MailTaskEntity.builder()
                .messageNumber(dto.getId())
                .from(new MailAddressEntity(dto.from))
                .subject(dto.getSubject())
                .content(dto.getContent())
                .numberOfAttachments(dto.getNumberOfAttachments())
                .build();
    }
}
