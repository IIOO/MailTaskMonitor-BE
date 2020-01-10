package com.monitor.task.business.controller;

import com.monitor.task.business.MailTaskMapper;
import com.monitor.task.business.dto.TaskPreviewDto;
import com.monitor.task.business.service.MailTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().body(mapped);
    }
}
