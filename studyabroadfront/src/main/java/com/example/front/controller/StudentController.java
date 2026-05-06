package com.example.front.controller;

import com.example.front.model.*;
import com.example.front.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // =================================================
    // 🔥 1. 註冊
    // =================================================
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            return Map.of("success", false, "message", "Email 已被註冊");
        }

        student.setPassword(encoder.encode(student.getPassword()));
        studentRepository.save(student);

        return Map.of("success", true, "message", "註冊成功");
    }

    // =================================================
    // 🔥 2. 國籍
    // =================================================
    @GetMapping("/countries")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

//    // =================================================
//    // 🔥 3. 登入
//    // =================================================
//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody Map<String, String> req) {
//
//        String email = req.get("email");
//        String password = req.get("password");
//
//        Student student = studentRepository.findByEmail(email);
//
//        if (student == null) {
//            return Map.of("success", false, "message", "帳號不存在");
//        }
//
//        if (!encoder.matches(password, student.getPassword())) {
//            return Map.of("success", false, "message", "密碼錯誤");
//        }
//
//        return Map.of(
//                "success", true,
//                "message", "登入成功",
//                "user", student
//        );
//    }

    // =================================================
    // 🔥 4. Profile（JPA 關聯乾淨版）
    // =================================================
    @GetMapping("/profile/{email}")
    public Map<String, Object> getProfile(@PathVariable String email) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return Map.of("success", false, "message", "查無學生");
        }

        // =============================
        // 1. 預約
        // =============================
        List<Appointments> appointmentsList =
                appointmentsRepository.findByEmail(email);

        // =============================
        // 2. 訂單（已 join fetch course + student）
        // =============================
        List<Orders> ordersList =
                ordersRepository.findByStudentId(student.getId());

        // =============================
        // 3. 組合 courseOrders（直接用關聯）
        // =============================
        List<Map<String, Object>> courseOrders = new ArrayList<>();

        for (Orders order : ordersList) {

            Courses course = order.getCourse(); // ⭐ JPA 關聯

            if (course == null) continue;

            Map<String, Object> obj = new HashMap<>();

            obj.put("orderId", order.getId());

            obj.put("courseName", course.getCourseName());
            obj.put("startDate", course.getStartDate());
            obj.put("endDate", course.getEndDate());

            obj.put("durationDays",
                    ChronoUnit.DAYS.between(course.getStartDate(), course.getEndDate()) + 1);

            obj.put("status", order.getOrderStatus());

            obj.put("price", order.getFinalFee());

            courseOrders.add(obj);
        }

        // =============================
        // 回傳
        // =============================
        return Map.of(
                "success", true,
                "student", student,
                "appointments", appointmentsList,
                "courseOrders", courseOrders
        );
    }
}