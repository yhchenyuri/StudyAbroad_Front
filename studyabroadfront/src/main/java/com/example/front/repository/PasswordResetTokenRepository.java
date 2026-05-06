package com.example.front.repository;

import com.example.front.model.PasswordResetToken;
import com.example.front.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    // 透過 Token 字串尋找（用於驗證連結是否有效）
    Optional<PasswordResetToken> findByToken(String token);
    
    // 透過學生尋找（用於生成新 Token 前先刪除舊的）
    Optional<PasswordResetToken> findByStudent(Student student);
}