package com.monitor.task.business.persistance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail_task_group")
@Getter
@Setter
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
