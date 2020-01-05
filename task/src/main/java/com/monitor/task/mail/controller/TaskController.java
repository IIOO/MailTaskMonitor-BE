package com.monitor.task.mail.controller;

import com.monitor.task.mail.dto.TaskDto;
import com.monitor.task.mail.dto.TaskPreviewDto;
import com.monitor.task.mail.TaskOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private TaskOperations taskOperations;

    @Autowired
    public TaskController(TaskOperations taskOperations) {
        this.taskOperations = taskOperations;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskPreviewDto>> getAllTasks() {
        return taskOperations.getAllTasks().map(list -> ResponseEntity.ok().body(list)).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/task/{messageNumber}")
    public ResponseEntity<TaskDto> getTaskByNumber(@PathVariable("messageNumber") final int messageNumber) {
        return taskOperations.getTask(messageNumber).map(task -> ResponseEntity.ok().body(task)).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/task/{messageNumber}/attachment/download")
    public ResponseEntity<Boolean> downloadAttachments(@PathVariable("messageNumber") final int messageNumber) {
        return ResponseEntity.ok().body(taskOperations.downloadMessageAttachments(messageNumber));
    }
}
