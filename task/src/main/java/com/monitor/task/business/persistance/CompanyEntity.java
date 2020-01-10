package com.monitor.task.business.persistance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {
    @Id
    private String name;

    @Builder
    public CompanyEntity(String name) {
        this.name = name;
    }
}
