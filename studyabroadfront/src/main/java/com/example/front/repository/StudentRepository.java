package com.example.front.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.front.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);
    
	boolean existsByEmail (String email);
	// → SELECT COUNT(*) > 0 FROM users WHERE email = ?
}
