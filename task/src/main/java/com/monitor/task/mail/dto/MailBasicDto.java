package com.monitor.task.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class MailBasicDto {
    private Long uid;
    private String subject;
    private String from;
    private LocalDateTime receivedDate;
}
