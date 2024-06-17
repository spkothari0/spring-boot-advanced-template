package com.shreyas.spring_boot_demo.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Setter
@Getter
public class APIResponse<T> {
    private int statusCode;
    private String status;
    private T data;
    private String message;
    private List<ErrorDetail> errors;
    private PaginationMetadata pagination;
    private Boolean isCached = false;

    public APIResponse(String status, T data, String message, HttpStatusCode code, Boolean isCached) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code.value();
        this.isCached = isCached;
    }

    public APIResponse(String status, T data, String message, HttpStatusCode code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code.value();
    }

    public APIResponse(String status, T data, String message, HttpStatusCode code, List<ErrorDetail> errors, PaginationMetadata pagination) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code.value();
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
