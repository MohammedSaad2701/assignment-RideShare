package com.example.rideshare.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        String errorDescription = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "VALIDATION_ERROR");
        errorResponse.put("message", errorDescription);
        errorResponse.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "NOT_FOUND");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "BAD_REQUEST");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "INTERNAL_ERROR");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}