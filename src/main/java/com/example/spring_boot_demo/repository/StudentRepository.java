package com.example.spring_boot_demo.repository;

import com.example.spring_boot_demo.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    List<StudentEntity> findByFirstName(String name);
    List<StudentEntity> findByEmail(String email);
}
