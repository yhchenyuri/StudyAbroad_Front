package com.example.front.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor 
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token; // 存忘記密碼用的 UUID

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private LocalDateTime expiryDate;

    // 建議保留這個「自定義建構子」，因為它含有邏輯（設定過期時間）
    public PasswordResetToken(String token, Student student, int expiryMinutes) {
        this.token = token;
        this.student = student;
        this.expiryDate = LocalDateTime.now().plusMinutes(expiryMinutes);
    }
    // 判斷 Token 是否過期的邏輯 (放在 Model 裡符合物件導向精神)
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}