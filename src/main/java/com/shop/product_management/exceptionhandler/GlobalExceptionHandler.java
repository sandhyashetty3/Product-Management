package com.shop.product_management.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return handleErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, IllegalArgumentException.class})
    public Map<String, Object> handleResourceNotFoundException(DataIntegrityViolationException ex) {
        return handleErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }
    
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleGlobalException(Exception ex) {
        return handleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private static Map<String, Object> handleErrorResponse(HttpStatus httpStatus, Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", httpStatus.value());
        response.put("errorMessage", ex.getMessage());
        return response;
    }
}
