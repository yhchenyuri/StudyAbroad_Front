package com.example.front.repository;

import com.example.front.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByEmail(String email);

    // 保留原本的方法，不影響其他 Java 檔
    Student findByEmail(String email);

    // 新增一個專門給密碼重設邏輯使用的方法
    Optional<Student> findOptionalByEmail(String email);
    
    
}