package com.monitor.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class TaskBasicInfo {
    public int id;
    public String subject;
    public String from;
}
