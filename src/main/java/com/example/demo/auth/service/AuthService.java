package com.example.demo.auth.service;

import com.example.demo.auth.dto.UserDTO;
import com.example.demo.auth.dto.UserLoginDTO;
import com.example.demo.auth.entity.User;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // 순서 잘 지키기
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),        // 이메일 위치 수정
                passwordEncoder.encode(userDTO.getPassword()),  // 비밀번호 위치 수정
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getCreatedAt());
    }

    public String[] loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userLoginDTO.getEmail());

        // 이메일로 이미 존재하는 회원인지 확인
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        // 로그인 시 비밀번호가 일치하지 않을 경우 예외를 던져 인증 실패
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 로그인 시 비밀번호가 일치할 경우 각각 액세스 토큰과 리프레시 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(existingUser.get().getEmail(), existingUser.get().getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(existingUser.get().getEmail());

        // 두 토큰을 클라이언트에 반환
        return new String[]{accessToken, refreshToken};
    }

    public String refreshToken (String refreshToken) {

        // 리프레시 토큰이 유효한지 검사
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        try {
            // 클레임이란?
            Claims claims = jwtUtil.getAllClaimsFromToken(refreshToken);

            // 토큰에서 사용자 정보 추출
            String userEmail = claims.getSubject();
            String username = claims.get("username", String.class);

            if (userEmail == null || userEmail.isEmpty()) {
                throw new IllegalArgumentException("Invalid refresh token: no user identifier found");
            }
            // 리프레시 토큰이 유효하면
            return jwtUtil.generateAccessToken(userEmail, username);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Refresh token has expired", e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing refresh token", e);
        }
    }
}
