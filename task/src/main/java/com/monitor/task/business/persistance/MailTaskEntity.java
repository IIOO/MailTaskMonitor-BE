package com.monitor.task.business.persistance;

import com.monitor.task.business.MailTaskStatus;
import com.monitor.task.user.persistance.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mail_task")
@Getter
@Setter
@NoArgsConstructor
public class MailTaskEntity {
    @Id
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private MailTaskGroupEntity group;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private MailAddressEntity from;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int numberOfAttachments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailTaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedDate;

    @Builder
    public MailTaskEntity(Long uid, MailTaskGroupEntity group, String subject, MailAddressEntity from, String content, int numberOfAttachments, LocalDateTime receivedDate) {
        this.uid = uid;
        this.group = group;
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.numberOfAttachments = numberOfAttachments;
        this.receivedDate = receivedDate;
        status = MailTaskStatus.TO_DO;
    }

    @PrePersist
    void onCreate() {
        creationDate = LocalDateTime.now();
    }
}
