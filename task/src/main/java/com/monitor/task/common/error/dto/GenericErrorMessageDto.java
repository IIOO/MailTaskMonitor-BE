package com.monitor.task.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GenericErrorMessageDto {
    private String errorDescription;
    private String exceptionName;
}
