package com.monitor.task.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailDto {
    public Long uid;
    public String subject;
    public String from;

    @Builder
    public MailDto(Long uid, String subject, String from) {
        this.uid = uid;
        this.subject = subject;
        this.from = from;
    }
}
