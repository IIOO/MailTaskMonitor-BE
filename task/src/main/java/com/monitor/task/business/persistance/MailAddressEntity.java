package com.monitor.task.business.persistance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mail_address")
@Getter
@Setter
@NoArgsConstructor
public class MailAddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @Builder
    public MailAddressEntity(String address, CompanyEntity company) {
        this.address = address;
        this.company = company;
    }

    public MailAddressEntity(String address) {
        this.address = address;
    }
}

