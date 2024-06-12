package com.shreyas.spring_boot_demo.controller;

import com.shreyas.spring_boot_demo.jwt.JwtUtils;
import com.shreyas.spring_boot_demo.business.bean.LoginRequest;
import com.shreyas.spring_boot_demo.business.bean.LoginResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class LoginController extends BaseController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        LoginResponse loginResponse = new LoginResponse(token, userDetails.getUsername(), roles);
        return SuccessResponse(loginResponse);
    }
}
