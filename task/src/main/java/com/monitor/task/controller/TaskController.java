package com.monitor.task.controller;

import com.monitor.task.dto.TaskDto;
import com.monitor.task.dto.TaskPreviewDto;
import com.monitor.task.TaskOperations;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskOperations taskOperations;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskPreviewDto>> getAllTasks() {
        return taskOperations.getAllTasks().map(list -> ResponseEntity.ok().body(list)).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/task/{messageNumber}")
    public ResponseEntity<TaskDto> getTaskByNumber(@PathVariable("messageNumber") final int messageNumber) {
        return taskOperations.getTask(messageNumber).map(task -> ResponseEntity.ok().body(task)).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/task/{messageNumber}/downloadAttachments")
    public ResponseEntity<Boolean> downoladAttachments(@PathVariable("messageNumber") final int messageNumber) {
        return ResponseEntity.ok().body(taskOperations.downloadMessageAttachments(messageNumber));
    }
}
