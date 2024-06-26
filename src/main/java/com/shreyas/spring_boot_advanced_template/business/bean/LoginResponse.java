package com.shreyas.spring_boot_advanced_template.business.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
                "jwtToken=hidden" +  + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles.toString() +
                '}';
    }
}
