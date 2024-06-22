package com.shreyas.spring_boot_advanced_template.repository.interfaces;

import com.shreyas.spring_boot_advanced_template.entity.CourseEntity;

import java.util.List;

public interface ICourseRepoCustom {
    boolean changeOccupiedSeats(Long courseId, int count);

    List<CourseEntity> getInvalidCourses();
}
