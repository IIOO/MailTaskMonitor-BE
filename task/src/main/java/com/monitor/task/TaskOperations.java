package com.monitor.task;

import com.monitor.task.dto.TaskDto;
import com.monitor.task.dto.TaskPreviewDto;
import com.monitor.task.service.AttachmentsDownloadService;
import com.monitor.task.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskOperations {
    @Autowired
    private MailService mailService;

    @Autowired
    private AttachmentsDownloadService attachmentsDownloadService;

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
