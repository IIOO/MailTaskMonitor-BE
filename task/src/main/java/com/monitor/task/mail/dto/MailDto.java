package com.monitor.task.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailDto {
    public int id;
    public String subject;
    public String from;

    @Builder
    public MailDto(int id, String subject, String from) {
        this.id = id;
        this.subject = subject;
        this.from = from;
    }
}
