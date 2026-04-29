package com.example.front.service;

import com.example.front.model.*;
import com.example.front.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 👉 註冊
    public String register(Student student) {

        // 檢查 Email
        if (studentRepository.existsByEmail(student.getEmail())) {
            return "EMAIL_EXISTS";
        }

        // 密碼加密
        String hash = encoder.encode(student.getPassword());
        student.setPassword(hash);

        studentRepository.save(student);

        return "SUCCESS";
    }

    // 👉 登入
    public boolean login(String email, String password) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return false;
        }
        return encoder.matches(password, student.getPassword());
    }

    // 👉 取得使用者
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}