package com.example.spring_boot_demo.repository;

import com.example.spring_boot_demo.entity.CourseEntity;

import java.util.List;

public interface ICourseRepoCustom {
    boolean changeOccupiedSeats(Long courseId, int count);

    List<CourseEntity> getInvalidCourses();
}
