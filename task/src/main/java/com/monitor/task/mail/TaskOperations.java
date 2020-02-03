package com.monitor.task.mail;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailAddressService;
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

    private final MailAddressService mailAddressService;


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
    public Optional<MailDto> getMail(int messageNumber) {
        Optional<Message> message = Optional.ofNullable(mailService.getMailByNumber(messageNumber));
        return message.map(MessageMapper::mapMessageToMailDto);
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

    /**
     * Fetch mails from mail server to database using filters:
     * subject has to match pattern
     * sender address has to be saved in database (company could have many configured addresses)
     * @return mapped messages saved in database
     */
    public List<MailDto> fetchMailsToDb() {
        List<MailTaskEntity> saved = new ArrayList<>();
        // get all addresses added to DB
        List<String> allowedAddresses = mailAddressService.getAll().stream()
                .map(MailAddressEntity::getAddress).collect(Collectors.toList());

        // get all mails with subject matching regex and within configured list of sender addresses, map them
        List<MailDto> mailDtos = mailService.getMailsWithMatchingSubject().stream()
                .map(MessageMapper::mapMessageToMailDto)
                .filter(dto -> allowedAddresses.contains(dto.getFrom()))
                .collect(Collectors.toList());

        log.info(mailDtos.size() + " mails fetched, saving... ");
        mailDtos.forEach(mail -> {
            saved.add(mailTaskService.saveMappedMessage(mail));
        });
        log.info("Saved " + saved.size() + " tasks to DB.");

        return saved.stream()
                .map(MailTaskMapper::mapMailTaskEntityToMailDto)
                .collect(Collectors.toList());
    }
}
