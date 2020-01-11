package com.monitor.task.business.persistance;

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

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private MailTaskGroupEntity group;


    @Builder
    public MailTaskHistoryEntity(UserEntity user, MailTaskGroupEntity group) {
        this.user = user;
        this.group = group;
    }

    @PrePersist
    void onCreate() {
        creationDate = LocalDateTime.now();
    }
}
