package com.example.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Spring Boot 會自動根據你的 application.properties 配置來注入這個物件
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        // 注意：這裡的 setFrom 最好跟你的 Gmail 帳號一致
        message.setFrom("yuhan.irene@gmail.com"); 
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("郵件已發送至: " + toEmail);
    }
}