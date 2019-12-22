package com.monitor.task;

import com.monitor.task.dto.TaskPreviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskOperations {
    @Autowired
    private MailService mailService;

    public Optional<List<TaskPreviewDto>> getAllTasks() {
        List<TaskPreviewDto> tasks = null;
        try {
            tasks = mailService.getMails().map(list -> list.stream().map(MessageMapper::mapMessageToTaskDto).collect(Collectors.toList())).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(tasks);
    }


    public Optional<TaskPreviewDto> getTask(int messageNumber) {
        TaskPreviewDto taskPreviewDto = null;
        try {
            taskPreviewDto = MessageMapper.mapMessageToTaskDto(mailService.getMailByNumber(messageNumber));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(taskPreviewDto);
    }
}
