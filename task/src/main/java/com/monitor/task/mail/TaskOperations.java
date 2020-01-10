package com.monitor.task.mail;

import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.mail.service.AttachmentsDownloadService;
import com.monitor.task.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskOperations {
    private MailService mailService;

    private AttachmentsDownloadService attachmentsDownloadService;

    @Autowired
    public TaskOperations(MailService mailService, AttachmentsDownloadService attachmentsDownloadService) {
        this.mailService = mailService;
        this.attachmentsDownloadService = attachmentsDownloadService;
    }


    public List<TaskPreviewDto> getAllMails() {
        return mailService.getMails().stream()
                .map(MessageMapper::mapMessageToTaskPrevievDto)
                .collect(Collectors.toList());
    }

    public Optional<TaskDto> getMail(int messageNumber) {
        Optional<Message> message = Optional.ofNullable(mailService.getMailByNumber(messageNumber));
        return message.map(MessageMapper::mapMessageToTaskDto);
    }

    public boolean downloadMessageAttachments(int messageNumber) {
        Message message = mailService.getMailByNumber(messageNumber);
        return attachmentsDownloadService.downloadAttachments(message);
    }
}
