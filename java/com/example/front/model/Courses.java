package com.example.front.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Data
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_name", length = 200, nullable = false)
    private String courseName;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "max_students")
    private Short maxStudents;

    @Column(name = "current_enrollment")
    private Short currentEnrollment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(columnDefinition = "text")
    private String remarks;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "is_active")
    private Boolean isActive;

}