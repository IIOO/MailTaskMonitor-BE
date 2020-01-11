package com.monitor.task.business.repository;

import com.monitor.task.business.persistance.MailTaskHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTaskHistoryRepository extends JpaRepository<MailTaskHistoryEntity, Long> {}
