package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class TaskBasicInfo {
    private Long uid;
    private Long orderNo;
    private String subject;
    private String from;
    private MailTaskStatus status;
}
