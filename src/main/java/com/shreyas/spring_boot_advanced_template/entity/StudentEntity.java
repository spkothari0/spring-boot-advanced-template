package com.shreyas.spring_boot_advanced_template.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "Student")
@Table(name = "Student",
        uniqueConstraints = {
            @UniqueConstraint(name = "email_unique_constraint",columnNames = {"email"})
        })
public class StudentEntity {
    @Id
    @SequenceGenerator(
            name = "student_seq",
            sequenceName = "student_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @Column(name = "studentId", updatable = false, nullable = false)
    private Long id;
    @Column(name = "firstName", nullable = false, columnDefinition = "VarChar(50)")
    private String firstName;
    @Column(name = "lastName", nullable = false, columnDefinition = "VarChar(50)")
    private String lastName;
    @Column(name = "email", nullable = false, columnDefinition = "VarChar(50)")
    private String email;
    @Column(name="dateOfBirth")
    private LocalDate dob=LocalDate.now();

    public StudentEntity(String firstName, String lastName, String email, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
    }

    public StudentEntity() { }
}
