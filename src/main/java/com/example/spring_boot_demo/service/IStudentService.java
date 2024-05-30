package com.example.spring_boot_demo.service;

import com.example.spring_boot_demo.business.bean.StudentBean;

import java.util.List;

public interface IStudentService {
    List<StudentBean> getAllStudents();

    StudentBean getStudentById(Long id);

    List<StudentBean> getStudentByName(String name);

    StudentBean getStudentByEmail(String email);

    StudentBean createStudent(StudentBean studentBean);

    StudentBean updateStudent(StudentBean studentBean);

    Boolean deleteStudent(Long id);
}
