package com.example.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 在開發階段，建議先關閉 CSRF 以排除干擾
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/login.css", "/register", "register.css", "/css/**", "/js/**", "/profile").permitAll()
                        .requestMatchers("/user/**").hasRole("USER") // 確保受保護的路徑需要權限
                        .anyRequest().authenticated() // 建議改為 authenticated 確保安全
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // 明確指定處理登入的 URL
                        .defaultSuccessUrl("/user/profile", true) // true 表示強制跳轉到成功頁
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}