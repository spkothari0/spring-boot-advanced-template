package com.shreyas.spring_boot_demo.filter;

import com.shreyas.spring_boot_demo.filter.correlation.CorrelationIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    private final CorrelationIdInterceptor correlationIdInterceptor;

    @Autowired
    public RestTemplateConfig(CorrelationIdInterceptor correlationIdInterceptor) {
        this.correlationIdInterceptor = correlationIdInterceptor;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(correlationIdInterceptor);
        restTemplate.getInterceptors().addAll(interceptors);
        return restTemplate;
    }
}
