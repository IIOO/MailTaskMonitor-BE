package com.monitor.task.business.repository;

import com.monitor.task.business.persistance.MailAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MailAddressRepository extends JpaRepository<MailAddressEntity, String> {
    Optional<MailAddressEntity> findByAddress(String address);

    List<MailAddressEntity> findMailAddressEntitiesByCompanyName(String companyName);
}
