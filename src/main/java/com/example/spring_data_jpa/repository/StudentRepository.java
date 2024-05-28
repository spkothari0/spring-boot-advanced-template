package com.example.spring_data_jpa.repository;

import com.example.spring_data_jpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    List<StudentEntity> findByFirstName(String name);
    List<StudentEntity> findByEmail(String email);
}
