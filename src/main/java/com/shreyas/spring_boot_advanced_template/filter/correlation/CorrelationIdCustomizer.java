package com.shreyas.spring_boot_advanced_template.filter.correlation;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class CorrelationIdCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod){
        operation.addParametersItem(new Parameter()
                .in("header")
                .name(CorrelationIdFilter.CORRELATION_ID_HEADER_NAME)
                .required(true)
                .description("Correlation ID")
                .schema(new StringSchema().format("uuid")));
        return operation;
    }
}
