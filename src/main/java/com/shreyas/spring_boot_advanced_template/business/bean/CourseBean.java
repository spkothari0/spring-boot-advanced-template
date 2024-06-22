package com.shreyas.spring_boot_advanced_template.business.bean;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseBean {
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 225, message = "Name cannot be more than 225 and less than 1 letter")
    private String name;
    private String description;
    @NotNull(message = "Semester cannot be null")
    private String semester;
    @NotNull(message = "Credits cannot be null")
    @Min(value = 1, message = "Credits cannot be less than 1")
    @Max(value = 5, message = "Credits cannot be greater than 10")
    private int credits;
    @Min(value = 10, message = "Seats cannot be less than 10")
    @Max(value = 100, message = "Seats cannot be greater than 100")
    private int totalSeats;
    private int occupiedSeats;

    public CourseBean() {
    }

    public CourseBean(String name, String description, String semester, int credits, int totalSeats, int occupiedSeats) {
        this.name = name;
        this.description = description;
        this.semester = semester;
        this.credits = credits;
        this.totalSeats = totalSeats;
        this.occupiedSeats = occupiedSeats;
    }

    private int getAvailableSeats() {
        return this.totalSeats - this.occupiedSeats;
    }

    public boolean isAvailable() {
        return this.getAvailableSeats() > 0;
    }

    public void assignSeat() {
        this.occupiedSeats++;
    }

    public void unassignSeat() {
        this.occupiedSeats--;
    }
}
