package com.shreyas.spring_boot_advanced_template.repository.interfaces;

import com.shreyas.spring_boot_advanced_template.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepo extends JpaRepository<CourseEntity, Long> {
    CourseEntity findByName(String courseName);
}
