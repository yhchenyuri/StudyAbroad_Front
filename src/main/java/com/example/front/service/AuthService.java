package com.example.front.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.front.model.Student;
import com.example.front.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public Student login(String email, String password) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            throw new IllegalArgumentException("EMAIL_NOT_FOUND");
        }

        if (!passwordEncoder.matches(password, student.getPassword())) {
            throw new IllegalArgumentException("PASSWORD_INCORRECT");
        }

        return student;
    }
}
