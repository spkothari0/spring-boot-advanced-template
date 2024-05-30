package com.example.spring_boot_demo.business.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class StudentBean {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;

    public StudentBean(Long id, String firstName, String lastName, String email, LocalDate dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
    }

    public StudentBean() {
    }

    public int getAge() {
        return LocalDate.now().getYear() - dob.getYear();
    }
}
