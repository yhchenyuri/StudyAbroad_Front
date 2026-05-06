package com.example.front.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.front.model.Appointments;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
	 boolean existsByEmail(String email); // 防重複
	List<Appointments> findByEmail(String email);
}

