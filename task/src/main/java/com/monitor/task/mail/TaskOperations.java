package com.monitor.task.mail;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailTaskService;
import com.monitor.task.mail.dto.MailDto;
import com.monitor.task.mail.service.AttachmentsDownloadService;
import com.monitor.task.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskOperations {
    private final MailService mailService;

    private final MailTaskService mailTaskService;

    private final AttachmentsDownloadService attachmentsDownloadService;


    /**
     * Get mails to display directly form mail server
     * @return list of mapped messages containing basic information to display
     */
    public List<MailDto> getAllMails() {
        return mailService.getMails().stream()
                .map(MessageMapper::mapMessageToMailDto)
                .collect(Collectors.toList());
    }

    /**
     * Get mail message details by it's number, directly from mail server
     * @param messageNumber number which identify message on mail server
     * @return mapped mail message details to display
     */
    public Optional<TaskDto> getMail(int messageNumber) {
        Optional<Message> message = Optional.ofNullable(mailService.getMailByNumber(messageNumber));
        return message.map(MessageMapper::mapMessageToTaskDto);
    }

    /**
     * Download attachments directly from mail server to local disc
     * Path is specified by class MailDownloadProperties
     * @param messageNumber number which identify message on mail server
     * @return true if there are some attachments
     */
    public boolean downloadMessageAttachments(int messageNumber) {
        Message message = mailService.getMailByNumber(messageNumber);
        return attachmentsDownloadService.downloadAttachments(message);
    }

    public List<TaskDto> fetchMailsToDb() {
        List<MailTaskEntity> saved = new ArrayList<>();

        List<TaskDto> taskDtos = mailService.getMailsWithMatchingSubject().stream()
                .map(MessageMapper::mapMessageToTaskDto)
                .collect(Collectors.toList());
        log.info(taskDtos.size() + " mails fetched, saving... ");
        taskDtos.forEach(task -> {
            saved.add(mailTaskService.saveMappedMailTaskToDb(task));
        });
        log.info("Saved " + saved.size() + " tasks to DB.");
        return saved.stream()
                .map(MailTaskMapper::mapMailTaskEntityToTaskDto)
                .collect(Collectors.toList());
    }
}
