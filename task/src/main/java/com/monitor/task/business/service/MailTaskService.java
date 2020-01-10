package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailTaskEntity;

import java.util.List;

public interface MailTaskService {
    MailTaskEntity save(MailTaskEntity mailTask);
    List<MailTaskEntity> findTasksByCompanyName(String companyName);
    List<MailTaskEntity> findTasksByFromAddress(String fromAddress);
    List<MailTaskEntity> findTasksByUserId(Long userId);
    List<MailTaskEntity> findAllUnassigned();
    List<MailTaskEntity> getAll();
    MailTaskEntity assignTaskToUser(Integer taskNumber, String user);
}