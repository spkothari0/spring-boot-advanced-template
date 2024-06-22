package com.shreyas.spring_boot_advanced_template.service.interfaces;

import com.shreyas.spring_boot_advanced_template.business.bean.StudentBean;

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
