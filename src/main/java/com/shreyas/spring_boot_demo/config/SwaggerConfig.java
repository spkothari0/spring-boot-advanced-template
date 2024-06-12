package com.shreyas.spring_boot_demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Spring Boot Demo",
        version = "1.0",
        description = "API documentation for Spring Boot Demo project",
        contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Shreyas Kothari", email = "spkothari968@gmail.com"),
        license = @io.swagger.v3.oas.annotations.info.License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
))
@SecuritySchemes({
        @io.swagger.v3.oas.annotations.security.SecurityScheme(
                name = "Bearer Authentication",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
public class SwaggerConfig {

    private final ApplicationContext applicationContext;
    private final OperationCustomizer operationCustomizer;

    @Autowired
    public SwaggerConfig(ApplicationContext applicationContext,CustomOperationCustomizer operationCustomizer) {
        this.applicationContext = applicationContext;
        this.operationCustomizer = operationCustomizer;
    }

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom-api")
                .packagesToScan("com.shreyas.spring_boot_demo.controller") // Specify your controllers' package
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }
}


