package com.hamo.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 끄기
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()          // 모든 요청 허용
                )
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인폼 비활성화
                .httpBasic(AbstractHttpConfigurer::disable); // HTTP Basic 비활성화
        return http.build();
    }

}
