package com.example.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Spring Boot 3.x 新版寫法：關閉 CSRF 防護（開發 API 時通常會先關閉）
            .csrf(csrf -> csrf.disable())
            // 設定所有的請求 (anyRequest) 都允許通行 (permitAll)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();
    }
}