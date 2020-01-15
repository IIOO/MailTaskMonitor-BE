package com.monitor.task.business.persistance;

import com.monitor.task.business.MailTaskStatus;
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
    private Long messageNumber;

    private Long orderNo;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private MailAddressEntity from;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int numberOfAttachments;

    @Enumerated(EnumType.STRING)
    private MailTaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private MailTaskGroupEntity group;

    @Builder
    public MailTaskEntity(Long messageNumber, Long orderNo, String subject, MailAddressEntity from, String content, int numberOfAttachments) {
        this.messageNumber = messageNumber;
        this.orderNo = orderNo;
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.numberOfAttachments = numberOfAttachments;
        status = MailTaskStatus.TO_DO;
    }
}
