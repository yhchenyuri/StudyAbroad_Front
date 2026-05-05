package com.example.front.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.front.dto.StudentResponse;
import com.example.front.model.Student;
import com.example.front.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody Map<String, String> request,
            HttpSession session) {

        String email = request.get("email");
        String password = request.get("password");

        Student student = authService.login(email, password);




        // ⭐ 存入 Session
        session.setAttribute("LOGIN_USER", student);

        return Map.of(
                "message", "login success",
                "user", student
        );
    }

    @GetMapping("/me")
    public Object getCurrentUser(HttpSession session) {

        Student student = (Student) session.getAttribute("LOGIN_USER");

        if (student == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        StudentResponse dto = new StudentResponse();
        dto.setId(student.getId());
        dto.setUuid(student.getUuid());
        dto.setNameZh(student.getNameZh());
        dto.setNameEn(student.getNameEn());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());

        return dto;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        return Map.of("message", "logout success");
    
    }

}