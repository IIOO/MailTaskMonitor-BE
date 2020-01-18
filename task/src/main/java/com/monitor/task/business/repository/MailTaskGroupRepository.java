package com.monitor.task.business.repository;

import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailTaskGroupRepository extends JpaRepository<MailTaskGroupEntity, Long> {
    Optional<MailTaskGroupEntity> findById(Long id);
    Optional<MailTaskGroupEntity> findMailTaskGroupEntityByOrderNoAndCompany(String orderNo, CompanyEntity company);
}
