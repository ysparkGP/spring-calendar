package org.example.calendarproject.api.exception;

import lombok.Data;
import org.example.calendarproject.core.exception.CalendarException;
import org.example.calendarproject.core.exception.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CalendarException.class)
    public ResponseEntity<ErrorResponse> handle(CalendarException ex){
        final ErrorCode errorCode = ex.getErrorCode();

        return new ResponseEntity<>(new ErrorResponse(errorCode, errorCode.getMessage()), ErrorHttpStatusMapper.mapToStatus(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex){
        final ErrorCode errorCode = ErrorCode.VALIDATION_FAIL;

        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode,
                        Optional.ofNullable(ex.getBindingResult().getFieldError())
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .orElse(errorCode.getMessage())
                        ),
                ErrorHttpStatusMapper.mapToStatus(errorCode));
    }

    @Data
    public static class ErrorResponse{
        private final ErrorCode errorCode;
        private final String errorMessage;
    }
}
