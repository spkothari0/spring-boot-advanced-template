package com.shreyas.spring_boot_advanced_template.service;

import com.shreyas.spring_boot_advanced_template.AppConstantConfig;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String ApplicationEmailId= "myprogram987@gmail.com";
    public static final String ApplicationName ="ShreyasSpring";
    public static final String ApplicationURL= AppConstantConfig.GetApplicationURL();

    public static final String VerificationAPI="auth/verification/";
}
