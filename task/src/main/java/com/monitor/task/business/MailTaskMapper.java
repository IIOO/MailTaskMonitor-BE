package com.monitor.task.business;

import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.mail.dto.MailDto;
import com.monitor.task.user.UserMapper;

import java.util.Optional;

public class MailTaskMapper {
    public static TaskPreviewDto mapMailTaskEntityToTaskPreviewDto(MailTaskEntity entity) {
        return TaskPreviewDto.builder()
                .uid(entity.getUid())
                .company(Optional.ofNullable(entity.getFrom().getCompany()).map(CompanyEntity::getName).orElse(null))
                .orderNo(entity.getGroup().getOrderNo())
                .build();
    }

    public static TaskDto mapMailTaskEntityToTaskDto(MailTaskEntity entity) {
        return TaskDto.taskBuilder()
                .uid(entity.getUid())
                .orderNo(entity.getGroup().getOrderNo())
                .from(entity.getFrom().getAddress())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .status(entity.getStatus())
                .numberOfAttachments(entity.getNumberOfAttachments())
                .assignedUser(Optional.ofNullable(entity.getUser()).map(UserMapper::mapUserEntityToUserDto).orElse(null))
                .receivedDate(entity.getReceivedDate())
                .build();
    }

    public static MailDto mapMailTaskEntityToMailDto(MailTaskEntity entity) {
        return MailDto.builder()
                .uid(entity.getUid())
                .orderNo(entity.getGroup().getOrderNo())
                .subject(entity.getSubject())
                .from(entity.getFrom().getAddress())
                .content(entity.getContent())
                .numberOfAttachments(entity.getNumberOfAttachments())
                .receivedDate(entity.getReceivedDate())
                .build();
    }
}
