package com.shreyas.spring_boot_advanced_template.repository.interfaces;

import com.shreyas.spring_boot_advanced_template.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepo extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findByFirstName(String name);

    StudentEntity findByEmail(String email);
}
