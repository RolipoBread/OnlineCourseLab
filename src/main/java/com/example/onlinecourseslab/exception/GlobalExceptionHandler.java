package com.example.onlinecourseslab.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(
        ResponseStatusException ex,
        HttpServletRequest request
    ) {
        log.error("ResponseStatusException: status={}, reason={}",
            ex.getStatusCode(), ex.getReason());

        final ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            ex.getStatusCode().value(),
            ex.getStatusCode().toString(),
            ex.getReason(),
            request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpServletRequest request
    ) {
        log.error("HttpMessageNotReadableException: {}", ex.getMessage());

        String message = "Invalid request format";

        if (ex.getCause() instanceof InvalidFormatException ife
            && ife.getTargetType() != null
            && ife.getTargetType().isEnum()) {

            final String enumName = ife.getTargetType().getSimpleName();
            final Object[] enumConstants = ife.getTargetType().getEnumConstants();
            final String allowedValues = Arrays.stream(enumConstants)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

            message = String.format("Invalid value '%s' for %s. Allowed values: %s",
                ife.getValue(),
                enumName,
                allowedValues);
        }

        final ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name(),
            message,
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        log.error("Validation error: {}", ex.getMessage());

        final String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.joining(", "));

        final ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name(),
            message,
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
        Exception ex,
        HttpServletRequest request
    ) {
        log.error("Unexpected error", ex);

        final ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.name(),
            "Internal server error: " + ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}