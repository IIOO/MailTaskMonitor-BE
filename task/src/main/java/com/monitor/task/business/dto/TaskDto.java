package com.monitor.task.business.dto;

import com.monitor.task.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class TaskDto extends TaskBasicInfo {
    String content;
    UserDto assignedUser;
    int attachments;

    @Builder
    public TaskDto(int id, String subject, String from, String content, UserDto assignedUser, int attachments) {
        super(id, subject, from);
        this.content = content;
        this.assignedUser = assignedUser;
        this.attachments = attachments;
    }
}
