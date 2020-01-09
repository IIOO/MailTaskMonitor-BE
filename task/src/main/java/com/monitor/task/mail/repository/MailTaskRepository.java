package com.monitor.task.mail.repository;

import com.monitor.task.mail.persistance.MailAddressEntity;
import com.monitor.task.mail.persistance.MailTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MailTaskRepository extends JpaRepository<MailTaskEntity, Integer> {
    Optional<MailTaskEntity> findById(Integer integer);

    List<MailTaskEntity> findMailTaskEntitiesByFrom(MailAddressEntity from);
}
