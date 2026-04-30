package com.example.front.dto;

import com.example.front.constant.OrderStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseOrder {

    private String courseName;

    private LocalDate courseStartDate;

    private LocalDate courseEndDate;

    private int durationDays;

    private OrderStatus orderStatus;

}
