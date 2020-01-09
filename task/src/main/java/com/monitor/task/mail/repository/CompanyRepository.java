package com.monitor.task.mail.repository;

import com.monitor.task.mail.persistance.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findById(String companyName);
}
