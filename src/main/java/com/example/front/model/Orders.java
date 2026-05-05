package com.example.front.model;

import com.example.front.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "char(36)", nullable = false, unique = true)
    private String uuid;

    //    @Column(name = "student_id")
//    private Integer studentId;
//    @Column(name = "course_id")
//    private Integer courseId;

    @ManyToOne
    @JoinColumn(name = "courses_id", insertable = false, updatable = false)
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "students_id", insertable = false, updatable = false)
    private Student student;

    @Column(name = "employees_id")
    private Integer employeesId;

    @Column(name = "agency_fee", precision = 10, scale = 2)
    private BigDecimal agencyFee;

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private BigDecimal discountRate;

    @Column(name = "final_fee", precision = 10, scale = 2)
    private BigDecimal finalFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}