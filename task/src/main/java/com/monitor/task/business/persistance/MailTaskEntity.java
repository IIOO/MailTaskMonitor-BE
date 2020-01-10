package com.monitor.task.business.persistance;

import com.monitor.task.user.persistance.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mail_task")
@Getter
@Setter
@NoArgsConstructor
public class MailTaskEntity {
    @Id
    private int messageNumber;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address")
    private MailAddressEntity from;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int numberOfAttachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private MailTaskGroupEntity group;

    @Builder
    public MailTaskEntity(int messageNumber, String subject, MailAddressEntity from, String content, int numberOfAttachments) {
        this.messageNumber = messageNumber;
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.numberOfAttachments = numberOfAttachments;
    }
}
