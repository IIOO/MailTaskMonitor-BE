package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import com.monitor.task.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TaskDto extends TaskBasicInfo {
    private String content;
    private UserDto assignedUser;
    private int numberOfAttachments;

    @Builder
    public TaskDto(int id, String subject, String from, MailTaskStatus status, String content, UserDto assignedUser, int numberOfAttachments) {
        super(id, subject, from, status);
        this.content = content;
        this.assignedUser = assignedUser;
        this.numberOfAttachments = numberOfAttachments;
    }
}
