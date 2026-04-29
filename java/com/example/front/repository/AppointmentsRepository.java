package com.example.front.repository;

import com.example.front.model.Appointments;
import com.example.front.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    List<Appointments> findByEmail(String email);
}