package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import com.monitor.task.mail.dto.MailDto;
import com.monitor.task.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TaskDto extends MailDto {
    private UserDto assignedUser;
    private MailTaskStatus status;
    private LocalDateTime createdDate;

    @Builder(builderMethodName = "taskBuilder")
    public TaskDto(Long uid, String subject, String from, LocalDateTime receivedDate, String orderNo, String content, int numberOfAttachments, UserDto assignedUser, MailTaskStatus status, LocalDateTime createdDate) {
        super(uid, subject, from, receivedDate, orderNo, content, numberOfAttachments);
        this.assignedUser = assignedUser;
        this.status = status;
        this.createdDate = createdDate;
    }
}
