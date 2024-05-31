package com.example.spring_boot_demo.business.bean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Setter
@Getter
public class StudentBean {
    private Long id;
    @Length(min = 3, max = 20, message = "First name must be between 3 to 20 characters")
    private String firstName;
    @Length(min = 3, max = 20, message = "Last name must be between 3 to 20 characters")
    private String lastName;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email is not valid")
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
