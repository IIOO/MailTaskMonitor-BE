package com.monitor.task.business.repository;

import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MailTaskRepository extends JpaRepository<MailTaskEntity, Integer> {
    Optional<MailTaskEntity> findByUid(Long uid);
    List<MailTaskEntity> findMailTaskEntitiesByFromAddress(String fromAddress);
    List<MailTaskEntity> findMailTaskEntitiesByGroup(MailTaskGroupEntity groupEntity);
    List<MailTaskEntity> findMailTaskEntitiesByUserId(Long userId);
    List<MailTaskEntity> findMailTaskEntitiesByUserIsNull();
}
