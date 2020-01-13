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
@Table(name = "mail_task_history")
@Getter
@Setter
@NoArgsConstructor
public class MailTaskHistoryEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private MailTaskEntity task;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private MailTaskGroupEntity group;

    private MailTaskStatus status;

    @Builder
    public MailTaskHistoryEntity(MailTaskEntity task, UserEntity user, MailTaskGroupEntity group, MailTaskStatus status) {
        this.task = task;
        this.user = user;
        this.group = group;
        this.status = status;
    }

    @PrePersist
    void onCreate() {
        creationDate = LocalDateTime.now();
    }
}
