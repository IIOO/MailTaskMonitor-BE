package com.monitor.task.business.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskPreviewDto {
    private Long uid;
    private String orderNo;
    private String company;

    @Builder
    public TaskPreviewDto(Long uid, String orderNo, String company) {
        this.uid = uid;
        this.orderNo = orderNo;
        this.company = company;
    }
}
