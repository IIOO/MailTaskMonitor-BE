package com.monitor.task.mail.persistance;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mail_address")
@NoArgsConstructor
public class MailAddressEntity {
    @Id
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Builder
    public MailAddressEntity(String address, CompanyEntity company) {
        this.address = address;
        this.company = company;
    }
}
