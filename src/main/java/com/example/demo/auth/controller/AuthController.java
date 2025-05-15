package com.example.demo.auth.controller;

import com.example.demo.auth.dto.AuthResponse;
import com.example.demo.auth.dto.RefreshRequest;
import com.example.demo.auth.dto.UserLoginDTO;
import com.example.demo.auth.service.AuthService;
import com.example.demo.board.controller.BoardController;
import com.example.demo.auth.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        logger.info("===== /register 엔드포인트 호출됨 =====");
        logger.info("post : /create" + userDTO.getUsername());

        UserDTO savedUser = authService.registerUser(userDTO);
        return ResponseEntity.ok().body(savedUser);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        logger.info("===== /login 엔드포인트 호출됨 =====");
        logger.info("post : /login" + userLoginDTO.getEmail());

        String[] tokens = authService.loginUser(userLoginDTO);
        return ResponseEntity.ok(new AuthResponse(tokens[0], tokens[1]));
    }

    // 액세스 토큰 만료일 경우 토큰 갱신 (새 액세스 토큰 발급)
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        logger.info("===== /refresh 엔드포인트 호출됨 =====");
        String newAccessToken = authService.refreshToken(refreshRequest.getRefreshToken());

        // 로그인 시 한번만 리프레시 토큰을 발급
        // 리프레시 토큰이 만료 때까지 계속 재사용
        return ResponseEntity.ok(new AuthResponse(newAccessToken, null));
    }

}
