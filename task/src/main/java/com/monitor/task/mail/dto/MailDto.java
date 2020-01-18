package com.monitor.task.mail.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MailDto extends MailBasicDto {
    private String orderNo;
    private String content;
    private int numberOfAttachments;

    @Builder
    public MailDto(Long uid, String subject, String from, LocalDateTime receivedDate, String orderNo, String content, int numberOfAttachments) {
        super(uid, subject, from, receivedDate);
        this.orderNo = orderNo;
        this.content = content;
        this.numberOfAttachments = numberOfAttachments;
    }
}
