package com.shreyas.spring_boot_demo.filter.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shreyas.spring_boot_demo.Annotations.HttpCacheable;
import com.shreyas.spring_boot_demo.controller.APIResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
//@Order(3)
public class CacheFilter implements Filter {

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/swagger-ui/",
            "/api-docs",
            "/swagger-resources",
            "/swagger-ui.html",
            "/webjars/",
            "/api/v1/auth/login");
    private final ApplicationContext applicationContext;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CacheFilter(StringRedisTemplate redisTemplate, ApplicationContext applicationContext) {
        this.redisTemplate = redisTemplate;
        this.applicationContext = applicationContext;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ObjectMapper objectMapper = new ObjectMapper();
        String requestURI = httpServletRequest.getRequestURI();
        boolean isExcluded = EXCLUDED_PATHS.stream().anyMatch(requestURI::startsWith);
        boolean isCacheable;
        try {
            isCacheable = isCacheable(httpServletRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (isExcluded || !isCacheable) {
            chain.doFilter(request, response);
            return;
        }

        // will ask the client to never store the response at client level and will force the client to revalidate with the server
        // and prevent client from storing cached copy of response
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);

        // check if response for the path key exists
        String cachedResponse = redisTemplate.opsForValue().get(requestURI);
        if (cachedResponse != null) {
            // Deserialize the cached response into APIResponse
            Gson gson = new Gson();
            APIResponse<?> apiResponse = gson.fromJson(cachedResponse, APIResponse.class);

            // Set isCached=true
            apiResponse.setIsCached(true);

            // Serialize APIResponse back into JSON
            String jsonResponse = gson.toJson(apiResponse);

            // Write the JSON response to the HTTP response
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = httpServletResponse.getWriter();
            out.write(jsonResponse);
            out.flush();
            return;
        }

        CachedHttpServletResponseWrapper responseWrapper = new CachedHttpServletResponseWrapper(httpServletResponse);
        chain.doFilter(request, responseWrapper);
        responseWrapper.flushBuffer();

        if (httpServletResponse.getStatus() == HttpServletResponse.SC_OK) {
            // Write the response to cache
            byte[] responseCopy = responseWrapper.getCopy();
            String jsonResponse = new String(responseWrapper.getCopy(), StandardCharsets.UTF_8);
            if(!jsonResponse.trim().isEmpty()){
                JsonNode jsonObject = objectMapper.readTree(jsonResponse);
                String serializedJson = objectMapper.writeValueAsString(jsonObject);
                redisTemplate.opsForValue().set(requestURI, serializedJson);
            }
            // Write the cached response back to the original response
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(responseCopy);
            outputStream.flush();
        }
    }

    private boolean isCacheable(HttpServletRequest request) throws Exception {

        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        HandlerExecutionChain handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
        if (handlerExecutionChain != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();
            // Check if the method is annotated with @HttpCacheable
            return handlerMethod.getMethodAnnotation(HttpCacheable.class) != null;
        }

        return false;
    }
}
