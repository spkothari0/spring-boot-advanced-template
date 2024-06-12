package com.shreyas.spring_boot_demo.business.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;

    public LoginResponse() {
        roles = new ArrayList<String>();
    }

    public LoginResponse(String jwtToken, String username, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "jwtToken='" + jwtToken + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles.toString() +
                '}';
    }
}
