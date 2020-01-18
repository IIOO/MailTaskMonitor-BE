package com.monitor.task.mail.controller;

import com.monitor.task.mail.TaskOperations;
import com.monitor.task.mail.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController {
    private TaskOperations taskOperations;

    @Autowired
    public MailController(TaskOperations taskOperations) {
        this.taskOperations = taskOperations;
    }

    @GetMapping
    public ResponseEntity<List<MailDto>> getAllTasks() {
        return ResponseEntity.ok(taskOperations.getAllMails());
    }

    @GetMapping("/{messageNumber}")
    public ResponseEntity<MailDto> getTaskByNumber(@PathVariable("messageNumber") final int messageNumber) {
        return taskOperations.getMail(messageNumber).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{messageNumber}/attachment/download")
    public ResponseEntity<Boolean> downloadAttachments(@PathVariable("messageNumber") final int messageNumber) {
        return ResponseEntity.ok(taskOperations.downloadMessageAttachments(messageNumber));
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<MailDto>> fetchFromMailToDb() {
        return ResponseEntity.ok(taskOperations.fetchMailsToDb());
    }
}
