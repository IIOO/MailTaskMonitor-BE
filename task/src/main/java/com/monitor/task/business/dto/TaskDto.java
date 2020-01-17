package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import com.monitor.task.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class TaskDto extends TaskBasicInfo {
    private String content;
    private UserDto assignedUser;
    private int numberOfAttachments;
    private LocalDateTime receivedDate;

    @Builder
    public TaskDto(Long uid, Long orderNo, String subject, String from, MailTaskStatus status, String content, UserDto assignedUser, int numberOfAttachments, LocalDateTime receivedDate) {
        super(uid, orderNo, subject, from, status);
        this.content = content;
        this.assignedUser = assignedUser;
        this.numberOfAttachments = numberOfAttachments;
        this.receivedDate = receivedDate;
    }
}
