package com.shreyas.spring_boot_demo.config;

import com.shreyas.spring_boot_demo.controller.BaseController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Spring Boot Demo",
        version = "1.0",
        description = "API documentation for Spring Boot Demo project",
        contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Shreyas Kothari", email = "spkothari968@gmail.com"),
        license = @io.swagger.v3.oas.annotations.info.License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
))
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
}


