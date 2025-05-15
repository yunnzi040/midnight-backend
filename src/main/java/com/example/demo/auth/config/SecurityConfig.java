
package com.example.demo.auth.config;

import com.example.demo.auth.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
    public class SecurityConfig {

    public final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()  // CSRF 보호 비활성화 (API 서버에서는 일반적)
                    .authorizeRequests()
                    .requestMatchers("/user/register", "/user/login", "/user/refresh", "/image/generate-image", "image/post").permitAll()  // 공개 API
                    .anyRequest().authenticated()  // 나머지 API는 인증 필요
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 이 부분 추가
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // 세션 사용 안 함 (JWT 사용)

            return http.build();
        }

    }
