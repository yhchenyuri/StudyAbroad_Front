package com.example.front.controller;

import com.example.front.dto.*;
import com.example.front.model.*;
import com.example.front.repository.*;
import com.example.front.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 👉 顯示註冊頁
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("countries", countryRepository.findAll());
        return "register";
    }

    // 👉 註冊
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Student student,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // ❌ Email 重複
        if (studentRepository.existsByEmail(student.getEmail())) {
            model.addAttribute("error", "Email 已被註冊");
            model.addAttribute("countries", countryRepository.findAll());
            return "register";
        }

        try {
            // 🔐 密碼加密
            student.setPassword(encoder.encode(student.getPassword()));

            // 💾 存資料
            studentRepository.save(student);

            // ✅ 成功訊息
            redirectAttributes.addFlashAttribute("msg", "註冊成功！");
            redirectAttributes.addFlashAttribute("msgType", "success");

            return "redirect:/register";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("msg", "註冊失敗，請稍後再試");
            redirectAttributes.addFlashAttribute("msgType", "danger");

            return "redirect:/register";
        }
    }

    // 👉 login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {

        Student student = studentRepository.findByEmail(email);

        // ❌ email 不存在
        if (student == null) {
            redirectAttributes.addFlashAttribute("msg", "帳號不存在");
            redirectAttributes.addFlashAttribute("msgType", "danger");
            return "redirect:/login";
        }

        // ❌ 密碼錯誤
        if (!encoder.matches(password, student.getPassword())) {
            redirectAttributes.addFlashAttribute("msg", "密碼錯誤");
            redirectAttributes.addFlashAttribute("msgType", "danger");
            return "redirect:/login";
        }

        // ✅ 成功
        return "redirect:/profile";
    }

    // 👉 profile
    @GetMapping("/profile")
    public String profile(Model model) {

        String email = "tzuhao.wang01@testmail.com";

        // Student
        Student student = studentRepository.findByEmail(email);
        model.addAttribute("student", student);

        // Appointments
        List<Appointments> appointmentsList = appointmentsRepository.findByEmail(email);
        model.addAttribute("appointmentsList", appointmentsList);

        // Orders
        List<Orders> ordersList = ordersRepository.findByStudentId(student.getId());

        // Course
        List<Integer> ids = ordersList.stream()
                .map(Orders::getCourseId)
                .toList();

        List<Courses> coursesList = coursesRepository.findByIdIn(ids);

        // Order & Course
        Map<Integer, Courses> courseMap = coursesList.stream()
                .collect(Collectors.toMap(
                        Courses::getId,
                        course -> course
                ));

        List<CourseOrder> courseOrdersList = new ArrayList<>();
        for (Orders orders : ordersList) {

            Courses courses = courseMap.get(orders.getCourseId());
            if (courses == null) {
                continue;
            }

            CourseOrder courseOrder = new CourseOrder();
            courseOrder.setCourseName(courses.getCourseName());
            courseOrder.setCourseStartDate(courses.getStartDate());
            courseOrder.setCourseEndDate(courses.getEndDate());
            courseOrder.setDurationDays((int) (ChronoUnit.DAYS.between(courses.getStartDate(), courses.getEndDate()) + 1));
            courseOrder.setOrderStatus(orders.getOrderStatus());

            courseOrdersList.add(courseOrder);
        }

        model.addAttribute("courseOrdersList", courseOrdersList);

        return "student_dashboard";
    }

}