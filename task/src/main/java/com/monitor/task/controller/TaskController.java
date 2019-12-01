package com.monitor.task.controller;

import com.monitor.task.TaskDto;
import com.monitor.task.TaskOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskOperations taskOperations;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return taskOperations.getAllTasks().map(list -> ResponseEntity.ok().body(list)).orElse(ResponseEntity.noContent().build());
    }
}
