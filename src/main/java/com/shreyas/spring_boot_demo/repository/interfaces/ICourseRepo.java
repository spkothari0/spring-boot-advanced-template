package com.shreyas.spring_boot_demo.repository.interfaces;

import com.shreyas.spring_boot_demo.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepo extends JpaRepository<CourseEntity, Long> {
    CourseEntity findByName(String courseName);
}
