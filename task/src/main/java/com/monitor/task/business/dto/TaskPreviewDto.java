package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import lombok.*;


@Getter
@NoArgsConstructor
@ToString
public class TaskPreviewDto extends TaskBasicInfo {

    @Builder
    public TaskPreviewDto(Long uid, Long orderNo, String subject, String from, MailTaskStatus status) {
        super(uid, orderNo, subject, from, status);
    }
}
