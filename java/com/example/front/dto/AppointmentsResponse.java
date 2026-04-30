package com.example.front.dto;

import lombok.Data;

@Data
public class AppointmentsResponse {

    private Integer id;
    private String uuid;

    private String name;
    private String phone;
    private String email;

    private Integer countryId;
    private String requirement;

    private String createdAt;
}