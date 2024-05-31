package com.example.spring_boot_demo.service;

import com.example.spring_boot_demo.business.bean.CourseBean;

import java.util.List;

public interface ICourseService {
    List<CourseBean> getAllCourses();

    CourseBean getCourseById(Long id);

    CourseBean addCourse(CourseBean courseBean);

    Boolean deleteCourse(Long id);

    CourseBean updateCourse(CourseBean courseBean, Long Id);

    CourseBean getCourseByCourseName(String name);

    boolean assignCourseSeat(Long courseId);

    boolean unassignCourseSeat(Long courseId);

    List<CourseBean> getInvalidCourses();
}
