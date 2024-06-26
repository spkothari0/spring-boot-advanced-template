package com.shreyas.spring_boot_advanced_template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class BaseController {

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    protected <T> ResponseEntity<APIResponse<T>> SuccessResponse(T body) {
        log.info("Returning success response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<APIResponse<T>> SuccessResponse(String message,T body) {
        log.info("Returning success response with body: {} and message: {}", body, message);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, message, HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> SuccessResponse() {
        log.info("Returning success response with no body");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> SuccessResponseMessage(String message) {
        log.info("Returning success response with no body and a message");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, message, HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> CreatedResponse() {
        log.info("Returning created response with no body");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<APIResponse<T>> CreatedResponse(T body) {
        log.info("Returning created response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<APIResponse<T>> NoContentResponse() {
        log.info("Returning no content response");
        APIResponse<T> response = new APIResponse<>("SUCCESS", null, "No content", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NO_CONTENT);
    }

    protected <T> ResponseEntity<APIResponse<T>> BadRequestResponse(T body) {
        log.warn("Returning bad request response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("FAILED", body, "Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<APIResponse<T>> NotFoundResponse(String message) {
        log.warn("Returning not found response with message: {}", message);
        APIResponse<T> response = new APIResponse<>("ERROR", null, message, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(T body, HttpStatus status, String message) {
        log.error("Returning error response with status: {} and message: {}", status, message);
        APIResponse<T> response = new APIResponse<>("ERROR", body, message, status);
        return new ResponseEntity<>(response, getJsonHeaders(), status);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(T body, String message) {
        log.error("Returning error response", HttpStatus.INTERNAL_SERVER_ERROR, message);
        APIResponse<T> response = new APIResponse<>("ERROR", body, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(String message) {
        log.error("Error Occured", HttpStatus.INTERNAL_SERVER_ERROR, message);
        APIResponse<T> response = new APIResponse<>("ERROR", null, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<APIResponse<T>> PaginatedResponse(T body, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        APIResponse.PaginationMetadata paginationMetadata = new APIResponse.PaginationMetadata(page, size, totalElements, totalPages);
        log.info("Returning paginated response with body: {} and pagination: {}", body, paginationMetadata);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK, null, paginationMetadata);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }
}
