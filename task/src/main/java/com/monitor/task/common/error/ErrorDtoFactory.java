package com.monitor.task.common.error;

import com.monitor.task.common.error.dto.ErrorDto;
import com.monitor.task.common.error.dto.GenericErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public class ErrorDtoFactory {

    private ErrorDtoFactory() {
    }

    public static ErrorDto createErrorDto(final Exception exception,
                                   final WebRequest webRequest,
                                   final HttpStatus status) {
        return ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .message(GenericErrorMessageDto.builder()
                        .errorDescription(getErrorDescription(exception))
                        .exceptionName(exception.getClass().getSimpleName())
                        .build())
                .path(webRequest.getContextPath())
                .build();
    }

    private static String getErrorDescription(final Exception exception) {
        return Optional.ofNullable(exception.getMessage())
                .orElse("No message available");
    }
}
