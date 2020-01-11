package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.persistance.MailTaskHistoryEntity;
import com.monitor.task.business.repository.MailAddressRepository;
import com.monitor.task.business.repository.MailTaskHistoryRepository;
import com.monitor.task.business.repository.MailTaskRepository;
import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailTaskServiceImpl implements MailTaskService {
    private final MailAddressRepository mailAddressRepository;

    private final MailTaskRepository mailTaskRepository;

    private final MailTaskHistoryRepository mailTaskHistoryRepository;

    private final UserService userService;

    @Override
    @Transactional
    public MailTaskEntity save(MailTaskEntity mailTask) {
        return mailTaskRepository.save(mailTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> findTasksByCompanyName(String companyName) {
        List<MailTaskEntity> allCompanyMails = new ArrayList<>();
        // find all email addresses related with given company
        List<MailAddressEntity> companyEmailAddresses = mailAddressRepository.findMailAddressEntitiesByCompanyName(companyName);

        // for every company email address fetch their mails
        companyEmailAddresses.forEach(address -> {
            List<MailTaskEntity> mailsFromAddress = mailTaskRepository.findMailTaskEntitiesByFromAddress(address.getAddress());
            allCompanyMails.addAll(mailsFromAddress);
        });
        return allCompanyMails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> findTasksByFromAddress(String fromAddress) {
        return mailTaskRepository.findMailTaskEntitiesByFromAddress(fromAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> findTasksByUserId(Long userId) {
        return mailTaskRepository.findMailTaskEntitiesByUserId(userId);
    }

    @Override
    @Transactional
    public MailTaskEntity assignTaskToUser(int taskNumber, String username) {
        UserEntity user = userService.findByUsername(username);
        MailTaskEntity mailTask = getByMessageNumber(taskNumber);

        mailTask.setUser(user);

        // create history record of change
        createMailTaskHistory(mailTask);
        return mailTaskRepository.save(mailTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> findAllUnassigned() {
        return mailTaskRepository.findMailTaskEntitiesByUserIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> getAll() {
        return mailTaskRepository.findAll();
    }

    private void createMailTaskHistory(MailTaskEntity task) {
        mailTaskHistoryRepository.save(MailTaskHistoryEntity.builder()
                .user(task.getUser())
                .group(task.getGroup())
                .build());
    }

    private MailTaskEntity getByMessageNumber(Integer messageNumber) {
        return mailTaskRepository.findByMessageNumber(messageNumber)
                .orElseThrow(() -> new NoResultException("No task with taskNumber: " + messageNumber + " found"));
    }
}
