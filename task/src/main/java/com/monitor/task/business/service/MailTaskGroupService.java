package com.monitor.task.business.service;

import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;

import java.util.List;

public interface MailTaskGroupService {
    List<MailTaskGroupEntity> getAll();
    MailTaskGroupEntity findOrCreate(String orderNo, CompanyEntity company);
}
