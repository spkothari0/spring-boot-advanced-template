package com.example.spring_boot_demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Spring Boot Demo",
        version = "1.0",
        description = "API documentation for Spring Boot Demo project",
        contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Shreyas Kothari", email = "spkothari968@gmail.com"),
        license = @io.swagger.v3.oas.annotations.info.License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom-api")
                .packagesToScan("com.example.spring_boot_demo.controller") // Specify your controllers' package
                .build();
    }
}


