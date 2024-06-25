package com.shreyas.spring_boot_advanced_template.filter.correlation;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
//@Order(1)
public class CorrelationIdFilter implements Filter {
    public static final String CORRELATION_ID_HEADER_NAME = "Correlation-Id";

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/swagger-ui/.*",
            "/api-docs.*",
            "/swagger-resources.*",
            "/swagger-ui.html.*",
            "/webjars/.*",
            "/api/v1/user/verification/.*");

    private static List<Pattern> compilePatterns(List<String> paths) {
        return paths.stream()
                .map(Pattern::compile)
                .toList();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getRequestURI();
        List<Pattern> EXCLUDED_PATTERNS = compilePatterns(EXCLUDED_PATHS);

        boolean isExcluded = EXCLUDED_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(path).matches());

        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }
        String correlationId = httpServletRequest.getHeader(CORRELATION_ID_HEADER_NAME);
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
