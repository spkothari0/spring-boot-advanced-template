package com.shreyas.spring_boot_advanced_template.aop.Logging;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class AppPointcuts {

    @Pointcut("within(com.shreyas.spring_boot_advanced_template.controller..*)")
    public void controllerPointcuts() {
    }

    @Pointcut("within(com.shreyas.spring_boot_advanced_template.service..*)")
    public void servicePointcuts() {
    }

    @Pointcut("within(com.shreyas.spring_boot_advanced_template.repository..*)")
    public void repositoryPointcuts() {
    }

    @Pointcut("controllerPointcuts() || servicePointcuts() || repositoryPointcuts()")
    public void mainPointcuts() {
    }
}
