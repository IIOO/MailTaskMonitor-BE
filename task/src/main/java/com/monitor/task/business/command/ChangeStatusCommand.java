package com.monitor.task.business.command;

import com.monitor.task.business.MailTaskStatus;
import lombok.Getter;

@Getter
public class ChangeStatusCommand {
    private MailTaskStatus status;
}
