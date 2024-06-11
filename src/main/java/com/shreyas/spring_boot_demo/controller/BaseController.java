package com.shreyas.spring_boot_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    protected <T> ResponseEntity<APIResponse<T>> SuccessResponse(T body) {
        logger.info("Returning success response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> SuccessResponse() {
        logger.info("Returning success response with no body");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, "Operation successful", HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> SuccessResponse(String message) {
        logger.info("Returning success response with no body and a message");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, message, HttpStatus.OK);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<APIResponse<Void>> CreatedResponse() {
        logger.info("Returning created response with no body");
        APIResponse<Void> response = new APIResponse<>("SUCCESS", null, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<APIResponse<T>> CreatedResponse(T body) {
        logger.info("Returning created response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Resource created successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<APIResponse<T>> NoContentResponse() {
        logger.info("Returning no content response");
        APIResponse<T> response = new APIResponse<>("SUCCESS", null, "No content", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NO_CONTENT);
    }

    protected <T> ResponseEntity<APIResponse<T>> BadRequestResponse(T body) {
        logger.warn("Returning bad request response with body: {}", body);
        APIResponse<T> response = new APIResponse<>("FAILED", body, "Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected <T> ResponseEntity<APIResponse<T>> NotFoundResponse(String message) {
        logger.warn("Returning not found response with message: {}", message);
        APIResponse<T> response = new APIResponse<>("ERROR", null, message, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(T body, HttpStatus status, String message) {
        logger.error("Returning error response with status: {} and message: {}", status, message);
        APIResponse<T> response = new APIResponse<>("ERROR", body, message, status);
        return new ResponseEntity<>(response, getJsonHeaders(), status);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(T body, String message) {
        logger.error("Returning error response", HttpStatus.INTERNAL_SERVER_ERROR, message);
        APIResponse<T> response = new APIResponse<>("ERROR", body, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<APIResponse<T>> ErrorResponse(String message) {
        logger.error("Error Occured", HttpStatus.INTERNAL_SERVER_ERROR, message);
        APIResponse<T> response = new APIResponse<>("ERROR", null, message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> ResponseEntity<APIResponse<T>> PaginatedResponse(T body, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        APIResponse.PaginationMetadata paginationMetadata = new APIResponse.PaginationMetadata(page, size, totalElements, totalPages);
        logger.info("Returning paginated response with body: {} and pagination: {}", body, paginationMetadata);
        APIResponse<T> response = new APIResponse<>("SUCCESS", body, "Operation successful", HttpStatus.OK, null, paginationMetadata);
        return new ResponseEntity<>(response, getJsonHeaders(), HttpStatus.OK);
    }
}
