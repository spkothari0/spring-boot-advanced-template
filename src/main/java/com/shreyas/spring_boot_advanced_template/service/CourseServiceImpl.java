package com.shreyas.spring_boot_advanced_template.service;

import com.shreyas.spring_boot_advanced_template.Utility.GenericBeanMapper;
import com.shreyas.spring_boot_advanced_template.business.bean.CourseBean;
import com.shreyas.spring_boot_advanced_template.entity.CourseEntity;
import com.shreyas.spring_boot_advanced_template.repository.interfaces.ICourseRepo;
import com.shreyas.spring_boot_advanced_template.repository.interfaces.ICourseRepoCustom;
import com.shreyas.spring_boot_advanced_template.service.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {
    private final ICourseRepo repo;
    private final ICourseRepoCustom customRepo;

    @Autowired
    public CourseServiceImpl(ICourseRepo repo, ICourseRepoCustom customRepo) {
        this.repo = repo;
        this.customRepo = customRepo;
    }

    public List<CourseBean> getAllCourses() {
        List<CourseBean> courseBeans;
        List<CourseEntity> entityList = repo.findAll();
        courseBeans = GenericBeanMapper.mapList(entityList, CourseBean.class);
        return courseBeans;
    }

    public CourseBean getCourseById(Long id) {
        Optional<CourseEntity> courseEntity = repo.findById(id);
        if (courseEntity.isPresent()) {
            return GenericBeanMapper.map(courseEntity.get(), CourseBean.class);
        } else {
            return null;
        }
    }

    public CourseBean addCourse(CourseBean courseBean) {
        CourseEntity courseEntity = GenericBeanMapper.map(courseBean, CourseEntity.class);
        courseEntity = repo.save(courseEntity);
        return GenericBeanMapper.map(courseEntity, CourseBean.class);
    }

    public Boolean deleteCourse(Long id) {
        Optional<CourseEntity> courseEntity = repo.findById(id);
        if (courseEntity.isPresent()) {
            CourseEntity course = courseEntity.get();
            repo.delete(course);
            return true;
        }
        return false;
    }

    public CourseBean updateCourse(CourseBean courseBean, Long id) {
        Optional<CourseEntity> courseEntity = repo.findById(id);
        if (courseEntity.isPresent()) {
            CourseEntity course = GenericBeanMapper.map(courseBean, CourseEntity.class);
            repo.save(course);
            return GenericBeanMapper.map(course, CourseBean.class);
        } else {
            return null;
        }
    }

    public CourseBean getCourseByCourseName(String name) {
        return GenericBeanMapper.map(repo.findByName(name), CourseBean.class);
    }

    public boolean assignCourseSeat(Long courseId) {
        return customRepo.changeOccupiedSeats(courseId, 1);
    }

    public boolean unassignCourseSeat(Long courseId) {
        return customRepo.changeOccupiedSeats(courseId, -1);
    }

    public List<CourseBean> getInvalidCourses() {
        return GenericBeanMapper.mapList(customRepo.getInvalidCourses(), CourseBean.class);
    }
}
