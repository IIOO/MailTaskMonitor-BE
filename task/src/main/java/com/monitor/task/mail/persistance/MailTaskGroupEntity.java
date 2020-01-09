package com.monitor.task.mail.persistance;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail_task_group")
@NoArgsConstructor
public class MailTaskGroupEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Builder
    public MailTaskGroupEntity(String name) {
        this.name = name;
    }
}
