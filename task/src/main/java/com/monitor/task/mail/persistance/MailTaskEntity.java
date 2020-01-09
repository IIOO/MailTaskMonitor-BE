package com.monitor.task.mail.persistance;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mail_task")
@NoArgsConstructor
public class MailTaskEntity {
    @Id
    private int messageNumber;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address")
    private MailAddressEntity from;

    private String content;

    private int numberOfAttachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private MailTaskGroupEntity group;

    @Builder
    public MailTaskEntity(int messageNumber, String subject, MailAddressEntity from, String content, int numberOfAttachments, MailTaskGroupEntity group) {
        this.messageNumber = messageNumber;
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.numberOfAttachments = numberOfAttachments;
        this.group = group;
    }
}
