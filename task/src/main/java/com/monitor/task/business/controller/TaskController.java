package com.monitor.task.business.controller;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.command.ChangeStatusCommand;
import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final MailTaskService mailTaskService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskPreviewDto>> getAllTasks() {
        List<TaskPreviewDto> mapped = mailTaskService.getAll().stream()
                .map(MailTaskMapper::mapMailTaskEntityToTaskPreviewDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<TaskPreviewDto>> getUnassigned() {
        List<TaskPreviewDto> mapped = mailTaskService.findAllUnassigned().stream()
                .map(MailTaskMapper::mapMailTaskEntityToTaskPreviewDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskPreviewDto>> getUserTasks(@PathVariable("userId") final Long userId) {
        List<TaskPreviewDto> mapped = mailTaskService.findTasksByUserId(userId).stream()
                .map(MailTaskMapper::mapMailTaskEntityToTaskPreviewDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapped);
    }

    // post because partial data change
    @PostMapping("{taskNumber}/assign")
    public ResponseEntity<TaskDto> assignToLogged(@PathVariable("taskNumber") final long taskUid,
                                                  @AuthenticationPrincipal Object principal) {
        MailTaskEntity updated = mailTaskService.assignTaskToUser(taskUid, ((UserDetails)principal).getUsername());
        return ResponseEntity.ok(MailTaskMapper.mapMailTaskEntityToTaskDto(updated));
    }

    // post because partial data change
    @PostMapping("{taskNumber}/status")
    public ResponseEntity<TaskDto> changeStatus(@PathVariable("taskNumber") final long taskUid,
                                                @RequestBody final ChangeStatusCommand command) {
        MailTaskEntity updated = mailTaskService.changeTaskStatus(taskUid, command.getStatus());
        return ResponseEntity.ok(MailTaskMapper.mapMailTaskEntityToTaskDto(updated));
    }
}
