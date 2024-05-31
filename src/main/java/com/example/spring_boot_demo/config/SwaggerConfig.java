package com.example.spring_boot_demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Spring Boot Demo", version = "1.0"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom-api")
                .packagesToScan("com.example.spring_boot_demo.controller") // Specify your controllers' package
                .build();
    }

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            paths.keySet().removeIf
                    (path -> path.matches("/profile.*") ||
                            path.matches("/studentEntities.*") ||
                            path.matches("/.*search.*"));
        };
    }
}


