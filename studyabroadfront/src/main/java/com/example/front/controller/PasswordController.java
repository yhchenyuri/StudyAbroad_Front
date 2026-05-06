package com.example.front.controller;

import com.example.front.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    /**
     * 第一步：使用者輸入 Email，請求重設密碼信
     * API: POST /api/password/forgot
     */
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "請提供 Email"));
        }

        try {
            passwordService.createPasswordResetToken(email);
            return ResponseEntity.ok(Map.of("message", "重設密碼信件已寄至您的信箱"));
        } catch (Exception e) {
        	// 關鍵：在後台悄悄印出錯誤原因（給開發者看）
            System.out.println("發信請求失敗（可能是 Email 不存在）：" + e.getMessage());
            
            // 但前端一律回傳 OK，不讓使用者知道該 Email 是否真的存在
        }
        
        return ResponseEntity.ok(Map.of("message", "若該帳號存在，重設信件已寄至您的信箱"));
    }

    /**
     * 第二步：使用者點擊連結，輸入新密碼後送出
     * API: POST /api/password/reset
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        System.out.println("接收到重設請求，Token: " + token);
        String newPassword = request.get("newPassword");

        if (token == null || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "請輸入完整的驗證資訊與密碼"));
        }

        try {
            // 呼叫 Service 更新密碼
            boolean success = passwordService.updatePassword(token, newPassword);
            
            if (success) {
                return ResponseEntity.ok(Map.of("message", "密碼更新成功，請使用新密碼登入"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "連結已失效或驗證碼不正確"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
}