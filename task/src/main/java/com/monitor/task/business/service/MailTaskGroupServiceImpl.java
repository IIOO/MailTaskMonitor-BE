package com.monitor.task.business.service;

import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;
import com.monitor.task.business.repository.MailTaskGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailTaskGroupServiceImpl implements MailTaskGroupService {
    private final MailTaskGroupRepository mailTaskGroupRepository;

    @Override
    public List<MailTaskGroupEntity> getAll() {
        return mailTaskGroupRepository.findAll();
    }

    @Override
    @Transactional
    public MailTaskGroupEntity findOrCreate(String orderNo, CompanyEntity company) {
        Optional<MailTaskGroupEntity> group = mailTaskGroupRepository.findMailTaskGroupEntityByOrderNoAndCompany(orderNo, company);
        return group.
                orElseGet(() -> mailTaskGroupRepository.save(MailTaskGroupEntity.builder()
                        .orderNo(orderNo)
                        .company(company).build()));
    }
}
