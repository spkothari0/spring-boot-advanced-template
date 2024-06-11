package com.shreyas.spring_boot_demo.controller;

import com.shreyas.spring_boot_demo.business.bean.StudentBean;
import com.shreyas.spring_boot_demo.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*")
public class StudentController extends BaseController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all students", description = "Get all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    public ResponseEntity<APIResponse<List<StudentBean>>> getStudents() {
        List<StudentBean> students = studentService.getAllStudents();
        if (students == null || students.isEmpty())
            return NoContentResponse();
        return PaginatedResponse(students, 0, 2, students.size());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by id", description = "Get a student by its id")
    public ResponseEntity<APIResponse<StudentBean>> getStudentById(@PathVariable Long id) {
        StudentBean student = studentService.getStudentById(id);
        if (student == null)
            return NotFoundResponse("Student not found");
        return SuccessResponse(student);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get student by email", description = "Get a student by its email")
    public ResponseEntity<APIResponse<StudentBean>> getStudentByEmail(@PathVariable String email) {
        StudentBean student = studentService.getStudentByEmail(email);
        if (student == null)
            return NotFoundResponse("Student not found");
        return SuccessResponse(student);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get student by first name", description = "Get a student by its first name")
    public ResponseEntity<APIResponse<List<StudentBean>>> getStudentByFirstName(@PathVariable String name) {
        List<StudentBean> students = studentService.getStudentByName(name);
        if (students == null)
            return NotFoundResponse("Students not found");
        return PaginatedResponse(students, 0, students.size(), students.size());
    }

    @PostMapping("/")
    @Operation(summary = "Create a student", description = "Create a student unless the email already exists")
    public ResponseEntity<APIResponse<StudentBean>> createStudent(@Valid @RequestBody StudentBean StudentBean) {
        StudentBean createdStudent = studentService.createStudent(StudentBean);
        if (createdStudent == null)
            return ErrorResponse("Student with same email already exists");
        return CreatedResponse(createdStudent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student", description = "Update a student if the id is found")
    public ResponseEntity<APIResponse<StudentBean>> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentBean studentBean) {
        Boolean isValidId = studentService.getStudentById(id) != null;

        if (isValidId) {
            studentBean.setId(id);
            StudentBean updatedStudent = studentService.updateStudent(studentBean);
            return SuccessResponse(updatedStudent);
        } else {
            return NotFoundResponse("Student not found");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Delete a student if the id is found")
    public ResponseEntity<APIResponse<Void>> deleteStudent(@PathVariable Long id) {
        Boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return SuccessResponse("Student deleted successfully");
        } else {
            return NotFoundResponse("Student not found");
        }
    }
}