package com.example.spring_boot_demo.controller;

import com.example.spring_boot_demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class BaseController {
    @Autowired
    protected StudentRepository studentRepository;

    @GetMapping("/home")
    public String home() {
        return "Hello World from base controller";
    }
}
