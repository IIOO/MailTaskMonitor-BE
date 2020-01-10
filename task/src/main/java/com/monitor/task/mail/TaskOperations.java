package com.monitor.task.mail;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.repository.MailAddressRepository;
import com.monitor.task.business.service.MailTaskService;
import com.monitor.task.mail.service.AttachmentsDownloadService;
import com.monitor.task.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
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

    private final MailAddressRepository mailAddressRepository;


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

    @Transactional
    public List<TaskDto> fetchMailsToDb() {
        //TODO fix design
        List<TaskDto> taskDtos = mailService.getMails().stream()
                .map(MessageMapper::mapMessageToTaskDto)
                .collect(Collectors.toList());
        log.info("Mails fetched, saving... " + taskDtos.size());
        log.info(taskDtos.toString());
        taskDtos.forEach(task -> {
            mailAddressRepository.saveAndFlush(new MailAddressEntity(task.getFrom()));
            mailTaskService.save(MailTaskMapper.mapTaskDtoToMailTaskEntity(task));
        });
        return taskDtos;
    }
}
