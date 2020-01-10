package com.monitor.task.business.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class TaskDto extends TaskBasicInfo {
    String content;
    int attachments;

    @Builder
    public TaskDto(int id, String subject, String from, String content, int attachments) {
        super(id, subject, from);
        this.content = content;
        this.attachments = attachments;
    }
}
