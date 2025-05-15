package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.service.BoardService;
import com.example.demo.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {

    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @Autowired
    public ImageUploadController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * MultipartFile을 통해 이미지 업로드 처리
     */
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "emotion", required = false) String emotion) {

        logger.info("Image upload requested for user: {}", userId);

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // MultipartFile에서 바이트 배열로 변환
            byte[] imageData = file.getBytes();
            
            // 이미지 저장
            BoardDTO boardDTO = boardService.saveImage(userId, imageData);
            
            // 감정 설정 (있는 경우)
            if (emotion != null && !emotion.isEmpty()) {
                boardDTO.setEmotion(emotion);
            }
            
            return ResponseEntity.ok(boardDTO);
            
        } catch (IOException e) {
            logger.error("Failed to process uploaded image: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to process uploaded image: " + e.getMessage());
        }
    }

    /**
     * Base64 인코딩된 이미지 업로드 처리 (모바일 앱이나 JavaScript에서 사용 가능)
     */
    @PostMapping(value = "/base64", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadBase64Image(@RequestBody Map<String, Object> request) {
        
        String base64Image = (String) request.get("image");
        Long userId = Long.valueOf(request.get("userId").toString());
        String emotion = (String) request.get("emotion");
        
        logger.info("Base64 image upload requested for user: {}", userId);

        if (base64Image == null || base64Image.isEmpty()) {
            return ResponseEntity.badRequest().body("No image data provided");
        }

        try {
            // Base64 문자열에서 바이트 배열로 변환
            byte[] imageData = ImageUtil.convertBase64ToByteArray(base64Image);
            
            // 이미지 저장
            BoardDTO boardDTO = boardService.saveImage(userId, imageData);
            
            // 감정 설정 (있는 경우)
            if (emotion != null && !emotion.isEmpty()) {
                boardDTO.setEmotion(emotion);
            }
            
            return ResponseEntity.ok(boardDTO);
            
        } catch (Exception e) {
            logger.error("Failed to process base64 image: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to process base64 image: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 테스트용 API (테스트 이미지 생성)
     */
    @GetMapping("/test-image")
    public ResponseEntity<?> getTestImage(@RequestParam("userId") Long userId) {
        try {
            // 더미 이미지 생성
            byte[] dummyImage = ImageUtil.createDummyImage();
            
            // 이미지 저장
            BoardDTO boardDTO = boardService.saveImage(userId, dummyImage);
            
            // 테스트용 정보 설정
            boardDTO.setEmotion("test");
            
            Map<String, Object> response = new HashMap<>();
            response.put("boardDTO", boardDTO);
            response.put("base64Image", ImageUtil.convertByteArrayToBase64(dummyImage, true, "png"));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to create test image: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to create test image: " + e.getMessage());
        }
    }
}
