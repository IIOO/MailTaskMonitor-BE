package com.monitor.task.business.service;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.MailTaskStatus;
import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.persistance.MailTaskHistoryEntity;
import com.monitor.task.business.repository.MailTaskHistoryRepository;
import com.monitor.task.business.repository.MailTaskRepository;
import com.monitor.task.user.persistance.UserEntity;
import com.monitor.task.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailTaskServiceImpl implements MailTaskService {
    private final MailTaskRepository mailTaskRepository;

    private final MailTaskHistoryRepository mailTaskHistoryRepository;

    private final MailAddressService mailAddressService;

    private final UserService userService;

    @Override
    @Transactional
    public MailTaskEntity save(MailTaskEntity mailTask) {
        return mailTaskRepository.save(mailTask);
    }

    @Transactional
    public MailTaskEntity saveMappedMailTaskToDb(TaskDto dto) {
        MailAddressEntity mailAddress = mailAddressService.findOrCreate(dto.getFrom());
        return save(MailTaskMapper.mapTaskDtoToMailTaskEntity(dto,  mailAddress));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MailTaskEntity> findTasksByCompanyName(String companyName) {
        List<MailTaskEntity> allCompanyMails = new ArrayList<>();
        // find all email addresses related with given company
        List<MailAddressEntity> companyEmailAddresses = mailAddressService.findByCompanyName(companyName);

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
    public MailTaskEntity assignTaskToUser(Long uid, String username) {
        UserEntity user = userService.findByUsername(username);
        MailTaskEntity mailTask = getByUid(uid);
        // create history record
        createMailTaskHistory(mailTask);

        mailTask.setUser(user);
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

    @Override
    @Transactional
    public MailTaskEntity changeTaskStatus(Long uid, MailTaskStatus status) {
        MailTaskEntity mailTask = getByUid(uid);

        if (!status.equals(mailTask.getStatus())) {
            // create history record
            createMailTaskHistory(mailTask);

            mailTask.setStatus(status);
            mailTask = mailTaskRepository.save(mailTask);
        } else {
            log.info("New status same as old, no changes.");
        }
        return mailTask;
    }

    /**
     * Create new entry in history table representing old values of MailTask
     * @param task object before update
     */
    private void createMailTaskHistory(MailTaskEntity task) {
        mailTaskHistoryRepository.save(MailTaskHistoryEntity.builder()
                .task(task)
                .user(task.getUser())
                .group(task.getGroup())
                .status(task.getStatus())
                .build());
    }

    private MailTaskEntity getByUid(Long uid) {
        return mailTaskRepository.findByUid(uid)
                .orElseThrow(() -> new NoResultException("No task with taskNumber: " + uid + " found"));
    }
}
