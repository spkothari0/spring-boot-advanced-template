package com.example.spring_boot_demo.controller;

import com.example.spring_boot_demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    @Autowired
    protected StudentRepository studentRepository;
}
