package com.monitor.task.common;

import com.monitor.task.common.error.ErrorDtoFactory;
import com.monitor.task.common.error.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorDto> handleGenericException(final Exception e, final WebRequest request){
        log.error(e.getClass().getSimpleName(), e);
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ErrorDtoFactory.createErrorDto(e, request, status), status);
    }
}
