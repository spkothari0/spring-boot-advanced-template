package com.shreyas.spring_boot_advanced_template.service;

import com.shreyas.spring_boot_advanced_template.AppConstant;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String ApplicationURL;
    private static AppConstant appConstant;

    public Constants(AppConstant appConstant) {
        Constants.appConstant = appConstant;
    }
    public static final String ApplicationEmailId= "myprogram987@gmail.com";
    public static final String ApplicationName ="ShreyasSpring";

    @PostConstruct
    public void init() {
        ApplicationURL = appConstant.GetApplicationURL();
    }
    public static final String VerificationAPI="user/verification/";
    public static final String AWSS3ProfileImagePrefix="ProfileImages_";
}
