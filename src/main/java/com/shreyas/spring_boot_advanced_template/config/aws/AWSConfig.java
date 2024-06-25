package com.shreyas.spring_boot_advanced_template.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.shreyas.spring_boot_advanced_template.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    private final AppConstant appConstant;
    @Autowired
    public AWSConfig(AppConstant appConstant) {
        this.appConstant = appConstant;
    }

    @Bean
    public AmazonS3 s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(appConstant.GetAWSAccessKey(), appConstant.GetAWSSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(appConstant.GetAWSS3Region()).build();
    }
}
