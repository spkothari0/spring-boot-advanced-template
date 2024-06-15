package com.shreyas.spring_boot_demo.exceptions;

import com.shreyas.spring_boot_demo.controller.APIResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleException(Exception e) {
        logger.error("Exception occurred: {}", e.getMessage(), e);
        APIResponse<String> response = new APIResponse<>("ERROR", null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException occurred: {}", ex.getMessage(), ex);
        APIResponse<String> response = new APIResponse<>("Forbidden", null, ex.getMessage() + ". Please contact the admin for access", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        logger.error("AuthenticationException occurred: {}", ex.getMessage(), ex);
        APIResponse<String> response = new APIResponse<>("ERROR", null, ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<Void>> handleResourceNotFoundException(RuntimeException ex) {
        logger.error("ResourceNotFoundException occurred: {}", ex.getMessage(), ex);
        APIResponse<Void> response = new APIResponse<>("ERROR", null, ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException occurred: {}", ex.getMessage(), ex);
        List<APIResponse.ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new APIResponse.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        APIResponse<Void> response = new APIResponse<>("ERROR", null, "Validation failed", HttpStatus.BAD_REQUEST, errorDetails, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIResponse<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("HttpRequestMethodNotSupportedException occurred: {}", ex.getMessage(), ex);
        APIResponse<String> response = new APIResponse<>("ERROR", null, ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("ConstraintViolationException occurred: {}", ex.getMessage(), ex);
        List<APIResponse.ErrorDetail> errorDetails = ex.getConstraintViolations()
                .stream()
                .map(violation -> new APIResponse.ErrorDetail(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        APIResponse<Void> response = new APIResponse<>("ERROR", null, "Validation failed", HttpStatus.BAD_REQUEST, errorDetails, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public  ResponseEntity<APIResponse<Void>> handleMissingRequestHeader(MissingRequestHeaderException ex ){
        logger.error("Missing Header Exception", ex.getMessage(),ex);
        APIResponse<Void> response = new APIResponse<>("ERROR", null, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
