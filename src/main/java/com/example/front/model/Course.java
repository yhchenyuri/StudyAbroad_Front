package com.example.front.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "courses", schema = "study_abroad_system")
@Data

public class Course implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "current_enrollment")
    private Integer currentEnrollment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration_days", insertable = false, updatable = false)
    private Integer durationDays;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Lob
    @Column(name="image",columnDefinition = "LONGBLOB")
    private byte[]image;

}

