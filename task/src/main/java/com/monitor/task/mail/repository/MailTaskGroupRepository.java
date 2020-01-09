package com.monitor.task.mail.repository;

import com.monitor.task.mail.persistance.MailTaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTaskGroupRepository extends JpaRepository<MailTaskGroupEntity, Long> {}
