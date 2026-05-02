package com.example.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.front.service.LoginUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,CorsConfigurationSource corsConfigurationSource) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())
//	        .cors(cors -> {})
	        .cors(cors -> cors.configurationSource(corsConfigurationSource))

	        .authorizeHttpRequests(auth -> auth

	            // ⭐ 前端靜態資源放行
	            .requestMatchers(
	                "/",
	                "/api/auth/**",
	                "/index.html",
	                "/**/*.js",
	                "/**/*.css"
	            ).permitAll()

	            // ⭐ API 放行
	            .requestMatchers("/api/auth/login").permitAll()
	            .requestMatchers("/api/auth/logout").permitAll()
	            .requestMatchers("/api/auth/me").permitAll()
	            .requestMatchers("/api/public/**").permitAll()
	            .requestMatchers("/api/appointments/**").permitAll()
	            .anyRequest().permitAll()

	            // ⭐ 其他都要登入
//	            .anyRequest().authenticated()
	        )

	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        );

	    return http.build();
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

