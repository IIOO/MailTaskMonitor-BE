package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;

public interface MailAddressService {
    MailAddressEntity findOrCreate(String address);
}
