package com.monitor.task.business.dto;

import com.monitor.task.business.MailTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class TaskBasicInfo {
    public Long id;
    public String subject;
    public String from;
    private MailTaskStatus status;
}
