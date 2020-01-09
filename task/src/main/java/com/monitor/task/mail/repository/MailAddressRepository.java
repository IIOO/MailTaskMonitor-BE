package com.monitor.task.mail.repository;

import com.monitor.task.mail.persistance.MailAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailAddressRepository extends JpaRepository<MailAddressEntity, String> {}
