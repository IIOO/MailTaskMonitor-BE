package com.monitor.task.business.controller;

import com.monitor.task.business.MailTaskGroupMapper;
import com.monitor.task.business.dto.TaskGroupDto;
import com.monitor.task.business.service.MailTaskGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class TaskGroupController {
    private final MailTaskGroupService mailTaskGroupService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskGroupDto>> getAllGroups() {
        List<TaskGroupDto> mapped = mailTaskGroupService.getAll().stream()
                .map(MailTaskGroupMapper::mapMailTaskGroupEntityToTaskGroupDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapped);
    }
}
