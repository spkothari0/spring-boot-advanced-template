package com.shreyas.spring_boot_advanced_template.controller;

import com.shreyas.spring_boot_advanced_template.Annotations.HttpCacheable;
import com.shreyas.spring_boot_advanced_template.business.bean.CourseBean;
import com.shreyas.spring_boot_advanced_template.service.interfaces.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CourseController extends BaseController {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all courses", description = "Get all courses")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @HttpCacheable
    public ResponseEntity<APIResponse<List<CourseBean>>> getCourses() {
        List<CourseBean> courses = courseService.getAllCourses();
        if (courses == null || courses.isEmpty())
            return NoContentResponse();
        return PaginatedResponse(courses, 0, 2, courses.size());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id", description = "Get course by id")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @HttpCacheable
    public ResponseEntity<APIResponse<CourseBean>> getCourseById(@NotNull @PathVariable Long id) {
        CourseBean course = courseService.getCourseById(id);
        if (course == null)
            return NotFoundResponse("Course not found");
        return SuccessResponse(course);
    }

    @PostMapping("/")
    @Operation(summary = "Post course", description = "Create a course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<CourseBean>> addCourse(@Valid @RequestBody CourseBean course) {
        CourseBean savedCourse = courseService.addCourse(course);
        if (savedCourse == null)
            return ErrorResponse("Failed to add Course");
        return SuccessResponse(savedCourse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update a course if the id is found")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<CourseBean>> updateCourse(@Valid @RequestBody CourseBean course, @NotNull @PathVariable Long id) {
        CourseBean updatedCourse = courseService.updateCourse(course, id);
        if (updatedCourse == null)
            return NotFoundResponse("Course not found");
        return SuccessResponse(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course by id", description = "Delete a course if the id is found")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> deleteCourse(@NotNull @PathVariable Long id) {
        Boolean deleted = courseService.deleteCourse(id);
        if (!deleted)
            return NotFoundResponse("Course not found");
        return SuccessResponseMessage("Successfully deleted course");
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get course by course name", description = "Get course by course name")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @HttpCacheable
    public ResponseEntity<APIResponse<CourseBean>> getCourseByCourseName(@NotNull @PathVariable String name) {
        CourseBean course = courseService.getCourseByCourseName(name);
        if (course == null)
            return NotFoundResponse("Course not found");
        return SuccessResponse(course);
    }

    @GetMapping("/assign/{courseId}")
    @Operation(summary = "Assign course", description = "Assign a course and reduce available seat by 1.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> assignCourse(@NotNull @PathVariable Long courseId) {

        if (courseService.getCourseById(courseId) == null)
            return NotFoundResponse("Course not found");

        if (!courseService.assignCourseSeat(courseId))
            return ErrorResponse("Failed to assign the course");
        return SuccessResponseMessage("Successfully assigned course");
    }

    @GetMapping("/unassign/{courseId}")
    @Operation(summary = "Unassign course", description = "Unassign a course and increase available seat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<Void>> unassignCourse(@NotNull @PathVariable Long courseId) {

        if (courseService.getCourseById(courseId) == null)
            return NotFoundResponse("Course not found");

        if (!courseService.unassignCourseSeat(courseId))
            return ErrorResponse("Failed to unassign the course");
        return SuccessResponseMessage("Successfully unassigned course");
    }

    @GetMapping("/invalid")
    @Operation(summary = "Get all invalid courses", description = "Get all courses for which no seats are assigned")
    @PreAuthorize("hasRole('ADMIN')")
    @HttpCacheable
    public ResponseEntity<APIResponse<List<CourseBean>>> getAllInvalidCourses() {
        List<CourseBean> courses = courseService.getInvalidCourses();
        if (courses == null || courses.isEmpty())
            return NoContentResponse();
        return PaginatedResponse(courses, 0, 2, courses.size());
    }
}
