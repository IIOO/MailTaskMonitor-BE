package com.monitor.task;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class TaskDto {
        public UUID id;
        public String subject;
        public String from;
}
