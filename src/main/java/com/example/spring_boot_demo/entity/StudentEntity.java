package com.example.spring_boot_demo.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "Student")
@Table(name = "Student",
        uniqueConstraints = {
            @UniqueConstraint(name = "email_unique_constraint",columnNames = {"email"})
        })
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "studentId", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "firstName", nullable = false, columnDefinition = "VarChar(255)")
    private String firstName;
    @Column(name = "lastName", nullable = false, columnDefinition = "VarChar(255)")
    private String lastName;
    @Column(name = "email", nullable = false, columnDefinition = "VarChar(255)")
    private String email;
    @Column(name="age")
    private int age;

    public StudentEntity(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public StudentEntity() { }
}
