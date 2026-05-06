package com.example.front.service;

import com.example.front.model.PasswordResetToken;
import com.example.front.model.Student;
import com.example.front.repository.PasswordResetTokenRepository;
import com.example.front.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;
    
 // 透過 @Value 讀取 properties 裡的設定，冒號後是預設值
    @Value("${frontend.url:http://localhost:8080}")
    private String frontendUrl;

    @Value("${password.reset.expiry-minutes:15}")
    private int expirationMinutes;

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security 的加密工具

    @Transactional
    public boolean updatePassword(String token, String newPassword) {
        // 1. 找 Token
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("驗證碼無效"));

        // 2. 檢查是否過期
        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken); // 過期了就刪掉
            return false;
        }

        // 3. 取得學生並更新密碼 (務必加密！)
        Student student = resetToken.getStudent();
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);

        // 4. 使用完畢，刪除 Token
        tokenRepository.delete(resetToken);

        return true;
    }
    
    @Transactional
    public void createPasswordResetToken(String email) {
        // 1. 檢查學生是否存在
        Student student = studentRepository.findOptionalByEmail(email)
                .orElseThrow(() -> new RuntimeException("若該帳號存在，重設信件已寄出"));

        // 2. 檢查是否已有舊 Token，有的話先刪除（確保一個學生同時只有一個有效 Token）
        tokenRepository.findByStudent(student).ifPresent(tokenRepository::delete);

        // 3. 生成 UUID Token
        String token = UUID.randomUUID().toString();

        // 4. 存入資料庫
        PasswordResetToken resetToken = new PasswordResetToken(token, student, expirationMinutes);
        tokenRepository.save(resetToken);

        // 5. 構建前端重設頁面的 URL (AngularJS 的路由)
        // 注意：這裡指向的是前端網址，token 當作參數
        String resetUrl = frontendUrl + "/reset-password.html?token=" + token;

        // 6. 寄出信件
        String subject = "海外短期留學課程：請重設您的密碼";
        String content = "您好，\n\n您收到這封郵件是因為我們收到了重設密碼的請求。"
                + "請點擊下方連結來完成操作：\n" 
                + resetUrl + "\n\n"
                + "如果您並未請求重設密碼，請忽略此郵件。";
        
        emailService.sendSimpleEmail(student.getEmail(), subject, content);
    }
}