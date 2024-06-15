package com.shreyas.spring_boot_demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {
    public static final String CORRELATION_ID_HEADER_NAME = "Correlation-Id";

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/swagger-ui/",
            "/api-docs",
            "/swagger-resources",
            "/swagger-ui.html",
            "/webjars/");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getRequestURI();
        boolean isExcluded = EXCLUDED_PATHS.stream().anyMatch(path::startsWith);

        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }
        String correlationId = httpServletRequest.getHeader(CORRELATION_ID_HEADER_NAME);
        System.out.println("Correlation ID from CorelationIDFilter: " + correlationId);
        if (correlationId == null || !isValidUUID(correlationId)) {
            throw new IllegalArgumentException("Correlation header is missing");
        }

        MDC.put(CORRELATION_ID_HEADER_NAME, correlationId);
        httpServletResponse.setHeader(CORRELATION_ID_HEADER_NAME, correlationId);
        try{
            filterChain.doFilter(request,response);
        }finally {
            MDC.remove(CORRELATION_ID_HEADER_NAME);
        }
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
