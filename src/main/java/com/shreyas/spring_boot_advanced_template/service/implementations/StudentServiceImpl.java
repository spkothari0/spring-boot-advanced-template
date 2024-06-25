package com.shreyas.spring_boot_advanced_template.service.implementations;

import com.shreyas.spring_boot_advanced_template.Utility.GenericBeanMapper;
import com.shreyas.spring_boot_advanced_template.business.bean.StudentBean;
import com.shreyas.spring_boot_advanced_template.entity.StudentEntity;
import com.shreyas.spring_boot_advanced_template.repository.interfaces.IStudentRepo;
import com.shreyas.spring_boot_advanced_template.service.interfaces.IStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {
    private final IStudentRepo repository;

    public StudentServiceImpl(IStudentRepo repository) {
        this.repository = repository;
    }

    public List<StudentBean> getAllStudents() {
        List<StudentBean> studentBeans;
        List<StudentEntity> entityList = repository.findAll();
        studentBeans = GenericBeanMapper.mapList(entityList, StudentBean.class);
        return studentBeans;
    }

    public StudentBean getStudentById(Long id) {
        Optional<StudentEntity> studentEntity = repository.findById(id);
        return studentEntity.map(entity -> GenericBeanMapper.map(entity, StudentBean.class)).orElse(null);
    }

    public List<StudentBean> getStudentByName(String name) {
        List<StudentBean> studentBeans;
        List<StudentEntity> entityList = repository.findByFirstName(name);
        studentBeans = GenericBeanMapper.mapList(entityList, StudentBean.class);
        return studentBeans;
    }

    public StudentBean getStudentByEmail(String email) {
        StudentBean studentBeans;
        StudentEntity entityList = repository.findByEmail(email);
        studentBeans = GenericBeanMapper.map(entityList, StudentBean.class);
        return studentBeans;
    }

    public StudentBean createStudent(StudentBean studentBean) {
        if (repository.findByEmail(studentBean.getEmail()) == null) {
            StudentEntity studentEntity = GenericBeanMapper.map(studentBean, StudentEntity.class);
            StudentEntity savedStudentEntity = repository.save(studentEntity);
            return GenericBeanMapper.map(savedStudentEntity, StudentBean.class);
        }
        return null;
    }

    public StudentBean updateStudent(StudentBean studentBean) {
        Optional<StudentEntity> studentEntity = repository.findById(studentBean.getId());
        if (studentEntity.isPresent()) {
            StudentEntity student = studentEntity.get();
            BeanUtils.copyProperties(studentBean, student);
            student = repository.save(student);
            return GenericBeanMapper.map(student, StudentBean.class);
        }
        return null;
    }

    public Boolean deleteStudent(Long id) {
        Optional<StudentEntity> studentEntity = repository.findById(id);
        if (studentEntity.isPresent()) {
            StudentEntity student = studentEntity.get();
            repository.delete(student);
            return true;
        }
        return false;
    }
}
