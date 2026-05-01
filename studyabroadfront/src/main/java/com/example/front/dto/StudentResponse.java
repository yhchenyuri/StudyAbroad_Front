package com.example.front.dto;

import com.example.front.model.Student;

import lombok.Data;

@Data
public class StudentResponse {

    private Integer id;
    private String uuid;
    private String nameZh;
    private String nameEn;
    private String email;
    private String phone;

    
}