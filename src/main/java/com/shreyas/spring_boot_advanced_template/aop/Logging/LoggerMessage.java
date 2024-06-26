package com.shreyas.spring_boot_advanced_template.aop.Logging;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggerMessage {
    private String className;
    private String methodName;
    private String methodArgs;

    @SneakyThrows
    @Override
    public String toString() {
        return "{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodArgs='" + methodArgs + '\'' +
                '}';
    }
}
