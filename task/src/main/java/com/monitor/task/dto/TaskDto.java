package com.monitor.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class TaskDto extends TaskBasicInfo {
    String content;

    @Builder
    public TaskDto(int id, String subject, String from, String content) {
        super(id, subject, from);
        this.content = content;
    }
}
