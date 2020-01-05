package com.monitor.task.mail;

import com.monitor.task.mail.dto.TaskDto;
import com.monitor.task.mail.dto.TaskPreviewDto;
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


    public Optional<List<TaskPreviewDto>> getAllTasks() {
        List<TaskPreviewDto> tasks = mailService.getMails().map(list -> list.stream().map(MessageMapper::mapMessageToTaskPrevievDto).collect(Collectors.toList())).orElse(null);
        return Optional.ofNullable(tasks);
    }

    public Optional<TaskDto> getTask(int messageNumber) {
        TaskDto taskPreviewDto = MessageMapper.mapMessageToTaskDto(mailService.getMailByNumber(messageNumber));
        return Optional.ofNullable(taskPreviewDto);
    }

    public boolean downloadMessageAttachments(int messageNumber) {
        Message message = mailService.getMailByNumber(messageNumber);
        return attachmentsDownloadService.downloadAttachments(message);
    }
}
