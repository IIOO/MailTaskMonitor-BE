package com.monitor.task.dto;

import lombok.*;


@Getter
@NoArgsConstructor
@ToString
public class TaskPreviewDto extends TaskBasicInfo {

    @Builder
    public TaskPreviewDto(int id, String subject, String from) {
        super(id, subject, from);
    }
}
