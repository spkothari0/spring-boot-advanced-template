package com.example.spring_boot_demo.repository;

import com.example.spring_boot_demo.entity.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepoCustomImpl implements ICourseRepoCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * @param courseId
     * @param count
     * @return boolean
     */
    @Override
    @Transactional
    public boolean changeOccupiedSeats(Long courseId, int count) {
        CourseEntity c = em.find(CourseEntity.class, courseId, LockModeType.PESSIMISTIC_WRITE);
        if (count > 0 && c != null && c.getOccupiedSeats() < c.getTotalSeats()) {
            c.setOccupiedSeats(c.getOccupiedSeats() + count);
            em.merge(c);
            return true;
        } else if (count < 0 && c != null && c.getOccupiedSeats() > 0) {
            c.setOccupiedSeats(c.getOccupiedSeats() + count);
            em.merge(c);
            return true;
        }
//        else
//            throw new RuntimeException("Invalid Operation. No available seats or course not found");
        return false;
    }


    /**
     * @return List<Course>
     */

    public List<CourseEntity> getInvalidCourses() {
        String jpql = "select c from Course c where c.occupiedSeats = 0";
        TypedQuery<CourseEntity> query = em.createQuery(jpql, CourseEntity.class);
        return query.getResultList();
    }
}
