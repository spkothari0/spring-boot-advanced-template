package com.example.spring_boot_demo.controller;

import com.example.spring_boot_demo.entity.StudentEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/students")
public class StudentController extends BaseController {

    @GetMapping
    public List<StudentEntity> getStudents() {
        return studentRepository.findAll();
    }

    // get a student by id
    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable Long id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if (studentEntity.isPresent()) {
            return ResponseEntity.ok(studentEntity.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    // get a student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<StudentEntity> getStudentByEmail(@PathVariable String email) {
        Optional<StudentEntity> studentEntity = studentRepository.findByEmail(email).stream().findAny();
        if (studentEntity.isPresent()) {
            return ResponseEntity.ok(studentEntity.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    // get a student by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<StudentEntity>> getStudentByName(@PathVariable String name) {
        List<StudentEntity> students = studentRepository.findByFirstName(name);
        if (!students.isEmpty()) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // create a student
    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity) {
        Optional<StudentEntity> optionalStudent=studentRepository.findByEmail(studentEntity.getEmail()).stream().findAny();
        StudentEntity student;
        if (optionalStudent.isPresent()) {
            System.out.println("The student with email:"+studentEntity.getEmail()+" already exists!");
            return ResponseEntity.badRequest().build();
        }
        else{
            student = studentRepository.save(studentEntity);
            return ResponseEntity.ok(student);
        }
    }

    // update a student
    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable Long id,@RequestBody StudentEntity studentEntity) {
        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            StudentEntity student = optionalStudent.get();
            student.setFirstName(studentEntity.getFirstName());
            student.setLastName(studentEntity.getLastName());
            student.setEmail(studentEntity.getEmail());
            student.setDob(studentEntity.getDob());

            return ResponseEntity.ok(studentRepository.save(student));
        }
        else
            return ResponseEntity.notFound().build();
    }

    // delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentEntity> deleteStudent(@PathVariable Long id) {
        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.delete(optionalStudent.get());
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

}
