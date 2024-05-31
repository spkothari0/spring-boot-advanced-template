package com.example.spring_boot_demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Course")
@Table(name = "Course")
public class CourseEntity {
    @Id
    @SequenceGenerator(
            name = "course_id_seq",
            sequenceName = "course_id_seq",
            allocationSize = 1,
            initialValue = 1000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    @Column(name = "courseId", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "VarChar(225)")
    private String name;
    @Column(name = "description", columnDefinition = "VarChar(255)")
    private String description;
    @Column(name = "semester", nullable = false, columnDefinition = "VarChar(25)")
    private String semester;
    @Column(name = "credits", nullable = false, columnDefinition = "Int")
    private int credits;
    @Column(name = "totalSeats", nullable = false, columnDefinition = "Int")
    private int totalSeats;
    @Column(name = "occupiedSeats", nullable = false, columnDefinition = "Int")
    private int occupiedSeats;

    public CourseEntity(String name, String description, String semester, int credits, int totalSeats, int occupiedSeats) {
        this.name = name;
        this.description = description;
        this.semester = semester;
        this.credits = credits;
        this.totalSeats = totalSeats;
        this.occupiedSeats = occupiedSeats;
    }

    public CourseEntity() {
    }
}
