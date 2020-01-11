package com.monitor.task.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private LocalDateTime timestamp;
    private Integer status;
    private GenericErrorMessageDto message;
    private String error;
    private String path;
}
