package com.monitor.task.business.dto;

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
    public TaskDto(int id, String subject, String from, String content, UserDto assignedUser, int numberOfAttachments) {
        super(id, subject, from);
        this.content = content;
        this.assignedUser = assignedUser;
        this.numberOfAttachments = numberOfAttachments;
    }
}
