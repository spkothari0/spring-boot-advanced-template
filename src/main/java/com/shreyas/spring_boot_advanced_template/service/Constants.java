package com.shreyas.spring_boot_advanced_template.service;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String ApplicationEmailId= "myprogram987@gmail.com";
    public static final String ApplicationName ="ShreyasSpring";
//    public static final String ApplicationURL= AppConstant.GetApplicationURL();
    public static final String ApplicationURL= "http://localhost:8080/api/v1/";
    public static final String VerificationAPI="user/verification/";
}
