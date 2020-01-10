package com.monitor.task.business.service;

import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.repository.MailAddressRepository;
import com.monitor.task.business.repository.MailTaskRepository;
import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailTaskServiceImpl implements MailTaskService {
    private final MailAddressRepository mailAddressRepository;

    private final MailTaskRepository mailTaskRepository;

    private final UserRepository userRepository;

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
    public MailTaskEntity assignTaskToUser(Integer taskNumber, String username) {
        MailTaskEntity updatedTask;
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(username);

        if (user.isPresent()) {
            updatedTask = mailTaskRepository.findByMessageNumber(taskNumber)
                    .map(task -> {
                        task.setUser(user.get());
                        return mailTaskRepository.save(task);
                    }).orElseThrow(() -> new NoResultException("No task with taskNumber: " + taskNumber + " found"));
        } else {
            throw new NoResultException("No user: " + username + " found");
        }
        return updatedTask;
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
}
