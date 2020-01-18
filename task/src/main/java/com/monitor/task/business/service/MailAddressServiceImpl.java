package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.repository.MailAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailAddressServiceImpl implements MailAddressService {
    private final MailAddressRepository mailAddressRepository;

    @Override
    public Optional<MailAddressEntity> findByAddress(String address) {
        return mailAddressRepository.findByAddress(address);
    }

    @Override
    public List<MailAddressEntity> findByCompanyName(String companyName) {
        return mailAddressRepository.findMailAddressEntitiesByCompanyName(companyName);
    }

    @Override
    public List<MailAddressEntity> getAll() {
        return mailAddressRepository.findAll();
    }
}
