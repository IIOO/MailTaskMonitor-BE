package com.monitor.task.business;

import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
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
                .attachments(entity.getNumberOfAttachments())
                .assignedUser(UserMapper.mapUserEntityToUserDto(entity.getUser()))
                .build();
    }
}
