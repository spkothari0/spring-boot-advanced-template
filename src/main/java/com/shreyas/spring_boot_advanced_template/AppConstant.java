package com.shreyas.spring_boot_advanced_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppConstant {
    private final Environment environment;

    @Autowired
    public AppConstant(Environment environment) {
        this.environment = environment;
    }

    public boolean RunStartupFile(){
        return Boolean.parseBoolean(environment.getProperty("SPRING_APP_RUN_STARTUP_FILE"));
    }

    public String GetApplicationURL() {
        return environment.getProperty("SPRING_APP_API_URL");
    }

    public String GetAWSAccessKey() {
        return environment.getProperty("AWS_ACCESS_KEY_ID");
    }

    public String GetAWSSecretAccessKey() {
        return environment.getProperty("AWS_SECRET_ACCESS_KEY");
    }

    public String GetAWSS3Region() {
        return environment.getProperty("AWS_S3_REGION");
    }

    public boolean AWSServiceEnabled() {
        return Boolean.parseBoolean(environment.getProperty("AWS_SERVICE_ENABLED"));
    }

    public String AWSS3BucketName() {
        return environment.getProperty("AWS_S3_BUCKET_NAME");
    }
}
