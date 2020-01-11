package com.monitor.task.business.controller;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.dto.TaskDto;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    private MailTaskService mailTaskService;

    @Autowired
    public TaskController(MailTaskService mailTaskService) {
        this.mailTaskService = mailTaskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskPreviewDto>> getAllTasks() {
        List<TaskPreviewDto> mapped = mailTaskService.getAll().stream()
                .map(MailTaskMapper::mapMailTaskEntityToTaskPreviewDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(mapped);
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
    public ResponseEntity<TaskDto> assignToLogged(@PathVariable("taskNumber") final int taskNumber,
                                                  @AuthenticationPrincipal Object principal) {
        MailTaskEntity updated = mailTaskService.assignTaskToUser(taskNumber, ((UserDetails)principal).getUsername());
        return ResponseEntity.ok(MailTaskMapper.mapMailTaskEntityToTaskDto(updated));
    }
}
