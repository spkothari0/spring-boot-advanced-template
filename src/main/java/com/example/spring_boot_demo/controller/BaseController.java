package com.example.spring_boot_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    protected <T> ResponseEntity<ApiResponse<T>> successResponse(T body) {
        logger.info("Returning success response with body: {}", body);
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse<Void>> successResponse() {
        logger.info("Returning success response with no body");
        ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse<Void>> successResponse(String message) {
        logger.info("Returning success response with no body and a message");
        ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null, message, HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse<Void>> createdResponse() {
        logger.info("Returning created response with no body");
        ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<ApiResponse<T>> createdResponse(T body) {
        logger.info("Returning created response with body: {}", body);
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", body, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<ApiResponse<T>> noContentResponse() {
        logger.info("Returning no content response");
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", null, "No content", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NO_CONTENT);
    }

    protected <T> ResponseEntity<ApiResponse<T>> badRequestResponse(T body) {
        logger.warn("Returning bad request response with body: {}", body);
        ApiResponse<T> response = new ApiResponse<>("FAILED", body, "Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<ApiResponse<T>> notFoundResponse(String message) {
        logger.warn("Returning not found response with message: {}", message);
        ApiResponse<T> response = new ApiResponse<>("ERROR", null, message, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(T body, HttpStatus status, String message) {
        logger.error("Returning error response with status: {} and message: {}", status, message);
        ApiResponse<T> response = new ApiResponse<>("ERROR", body, message, status);
        return new ResponseEntity<>(response, getJsonHeaders(), status);
    }

    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(T body, String message) {
        logger.error("Returning error response", HttpStatus.INTERNAL_SERVER_ERROR, message);
        ApiResponse<T> response = new ApiResponse<>("ERROR", body, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(String message) {
        logger.error("Error Occured", HttpStatus.INTERNAL_SERVER_ERROR, message);
        ApiResponse<T> response = new ApiResponse<>("ERROR", null, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<ApiResponse<T>> paginatedResponse(T body, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        ApiResponse.PaginationMetadata paginationMetadata = new ApiResponse.PaginationMetadata(page, size, totalElements, totalPages);
        logger.info("Returning paginated response with body: {} and pagination: {}", body, paginationMetadata);
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK, null, null, paginationMetadata);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ApiResponse<T>> withLinks(T body, Map<String, String> links) {
        logger.info("Returning response with links and body: {}", body);
        ApiResponse<T> response = new ApiResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK, links, null, null);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        logger.error("Exception occurred: {}", e.getMessage(), e);
        ApiResponse<String> response = new ApiResponse<>("ERROR", null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
