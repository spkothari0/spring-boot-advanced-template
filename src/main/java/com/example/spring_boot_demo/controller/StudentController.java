package com.example.spring_boot_demo.controller;

import com.example.spring_boot_demo.business.bean.StudentBean;
import com.example.spring_boot_demo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@BasePathAwareController(path = "/students")
public class StudentController extends BaseController {

    @Autowired
    private IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<StudentBean>>> getStudents() {
        List<StudentBean> students = studentService.getAllStudents();
        if (students == null || students.isEmpty())
            return noContentResponse();
        return paginatedResponse(students, 0, 2, students.size());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentBean>> getStudentById(@PathVariable Long id) {
        StudentBean student = studentService.getStudentById(id);
        if (student == null)
            return notFoundResponse("Student not found");
        return successResponse(student);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentBean>> getStudentByEmail(@PathVariable String email) {
        StudentBean student = studentService.getStudentByEmail(email);
        if (student == null)
            return notFoundResponse("Student not found");
        return successResponse(student);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<StudentBean>>> getStudentByFirstName(@PathVariable String name) {
        List<StudentBean> students = studentService.getStudentByName(name);
        if (students == null)
            return notFoundResponse("Students not found");
        return paginatedResponse(students, 0, students.size(), students.size());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentBean>> createStudent(@RequestBody StudentBean StudentBean) {
        StudentBean createdStudent = studentService.createStudent(StudentBean);
        if (createdStudent == null)
            return errorResponse("Student with same email already exists");
        return createdResponse(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentBean>> updateStudent(@PathVariable Long id, @RequestBody StudentBean studentBean) {
        Boolean isValidId = studentService.getStudentById(id) != null;

        if (isValidId) {
            studentBean.setId(id);
            StudentBean updatedStudent = studentService.updateStudent(studentBean);
            return successResponse(updatedStudent);
        } else {
            return notFoundResponse("Student not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        Boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return successResponse("Student deleted successfully");
        } else {
            return notFoundResponse("Student not found");
        }
    }
}