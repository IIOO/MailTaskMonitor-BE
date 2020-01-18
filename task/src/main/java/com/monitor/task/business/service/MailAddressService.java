package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;

import java.util.List;
import java.util.Optional;

public interface MailAddressService {
    List<MailAddressEntity> getAll();
    Optional<MailAddressEntity> findByAddress(String address);
    List<MailAddressEntity> findByCompanyName(String companyName);
}
