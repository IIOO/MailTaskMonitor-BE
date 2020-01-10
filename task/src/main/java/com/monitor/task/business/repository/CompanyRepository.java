package com.monitor.task.business.repository;

import com.monitor.task.business.persistance.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findById(String companyName);
}
