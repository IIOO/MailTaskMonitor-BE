package com.monitor.task.mail.persistance;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
@NoArgsConstructor
public class CompanyEntity {
    @Id
    private String name;

    @Builder
    public CompanyEntity(String name) {
        this.name = name;
    }
}
