package com.shreyas.spring_boot_advanced_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppConstantConfig {
    private static Environment environment;

    @Autowired
    public AppConstantConfig(Environment environment) {
        AppConstantConfig.environment = environment;
    }

    public static String GetApplicationURL() {
        return environment.getProperty("SPRING_APP_API_URL");
    }
}
