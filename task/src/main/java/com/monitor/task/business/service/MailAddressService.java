package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;

import java.util.List;

public interface MailAddressService {
    MailAddressEntity findOrCreate(String address);
    List<MailAddressEntity> findByCompanyName(String companyName);
}
