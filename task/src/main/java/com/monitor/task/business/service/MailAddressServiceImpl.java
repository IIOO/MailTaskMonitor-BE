package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.repository.MailAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailAddressServiceImpl implements MailAddressService {
    private final MailAddressRepository mailAddressRepository;

    @Override
    @Transactional
    public MailAddressEntity findOrCreate(String address) {
        Optional<MailAddressEntity> mailAddress = mailAddressRepository.findByAddress(address);
        return mailAddress.orElseGet(() -> mailAddressRepository.save(MailAddressEntity.builder().address(address).build()));
    }
}
