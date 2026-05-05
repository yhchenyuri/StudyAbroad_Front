package com.example.front.repository;

import com.example.front.model.Orders;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("""
    select o from Orders o
    join fetch o.course
    join fetch o.student
    where o.student.id = :studentId
""")
    List<Orders> findByStudentId(Integer studentId);
}