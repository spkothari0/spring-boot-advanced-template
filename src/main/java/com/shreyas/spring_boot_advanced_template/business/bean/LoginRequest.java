package com.shreyas.spring_boot_advanced_template.business.bean;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password =  password;
    }

    public LoginRequest() {}
}
