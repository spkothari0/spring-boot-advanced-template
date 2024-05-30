package com.example.spring_boot_demo.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ApiResponse<T> {
    private int statusCode;
    private String status;
    private T data;
    private String message;
    private Map<String, String> links;
    private List<ErrorDetail> errors;
    private PaginationMetadata pagination;

    public ApiResponse(String status, T data, String message, HttpStatusCode code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code.value();
    }

    public ApiResponse(String status, T data, String message, HttpStatusCode code, Map<String, String> links, List<ErrorDetail> errors, PaginationMetadata pagination) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code.value();
        this.links = links;
        this.errors = errors;
        this.pagination = pagination;
    }

    @Setter
    @Getter
    public static class ErrorDetail {
        private String code;
        private String message;

        public ErrorDetail(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Setter
    @Getter
    public static class PaginationMetadata {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

        public PaginationMetadata(int page, int size, long totalElements, int totalPages) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

    }
}
