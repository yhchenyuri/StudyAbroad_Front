package com.example.front.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.front.dto.AppointmentsRequest;
import com.example.front.dto.AppointmentsResponse;
import com.example.front.model.Appointments;
import com.example.front.repository.AppointmentsRepository;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository repo;

    // 新增
    public Appointments create(AppointmentsRequest req) {

        Appointments a = new Appointments();

        a.setName(req.getName());
        a.setPhone(req.getPhone());
        a.setEmail(req.getEmail());
        a.setCountryId(req.getCountryId());
        a.setRequirement(req.getRequirement());

        a.setUuid(UUID.randomUUID().toString());

        return repo.save(a);
    }

    // 查詢
    public List<Appointments> getAll() {
        return repo.findAll();
    }

    // 刪除
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    // ================= DTO 轉換 =================

    public AppointmentsResponse toResponse(Appointments a) {

        AppointmentsResponse res = new AppointmentsResponse();

        res.setId(a.getId());
        res.setUuid(a.getUuid());
        res.setName(a.getName());
        res.setPhone(a.getPhone());
        res.setEmail(a.getEmail());
        res.setCountryId(a.getCountryId());
        res.setRequirement(a.getRequirement());

        res.setCreatedAt(
            a.getCreatedAt() != null
                ? a.getCreatedAt().toString()
                : null
        );

        return res;
    }

    public List<AppointmentsResponse> toResponseList(List<Appointments> list) {

        return list.stream()
                .map(this::toResponse)
                .toList();
    }
}

