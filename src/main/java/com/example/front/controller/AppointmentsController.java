package com.example.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.front.dto.ApiResponse;
import com.example.front.dto.AppointmentsRequest;
import com.example.front.dto.AppointmentsResponse;
import com.example.front.model.Appointments;
import com.example.front.service.AppointmentsService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentsController {

    @Autowired
    private AppointmentsService service;

    // 新增
    @PostMapping
    public ApiResponse<AppointmentsResponse> create(
            @Valid @RequestBody AppointmentsRequest req) {

        Appointments a = service.create(req);

        return ApiResponse.success(service.toResponse(a));
    }

    // 查詢全部
    @GetMapping
    public ApiResponse<List<AppointmentsResponse>> getAll() {

        return ApiResponse.success(
            service.toResponseList(service.getAll())
        );
    }

    // 刪除
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Integer id) {

        service.delete(id);

        return ApiResponse.success(null);
    }
}
