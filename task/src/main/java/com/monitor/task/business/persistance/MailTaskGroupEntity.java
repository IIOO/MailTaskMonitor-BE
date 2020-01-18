package com.monitor.task.business.persistance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mail_task_group")
@Getter
@Setter
@NoArgsConstructor
public class MailTaskGroupEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String orderNo;

    @ManyToOne
    private CompanyEntity company;

    @Builder
    public MailTaskGroupEntity(String orderNo, CompanyEntity company) {
        this.orderNo = orderNo;
        this.company = company;
    }
}
