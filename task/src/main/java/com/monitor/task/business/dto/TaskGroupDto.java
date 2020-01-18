package com.monitor.task.business.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskGroupDto {
    private String orderNo;
    private String companyName;
}
