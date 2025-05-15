package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class BoardController {

    private final BoardService boardService;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    public BoardController(BoardService boardService, RestTemplate restTemplate) {
        this.boardService = boardService;
        this.restTemplate = restTemplate;
    }

    // 1. 유니티에서 텍스트와 사용자 아이디
    // 2. 텍스트 ai
    // 3. ai 반환
    // 2) 바이트 배열
    // 바이트 배열을 DB에 저장 후 REST api로 바이트 배열을 유니티로 전송
    @PostMapping("/generate-image")
    public ResponseEntity<?> generateImage(@RequestBody Map<String, Object> request) {
        logger.info("===== /generate-image 엔드포인트 호출됨 =====");
        logger.info("요청 데이터: {}", request);

        String prompt = (String) request.get("prompt");

        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("prompt should not be null");
        }

        try {
            // AI 서버 호출
            String aiUrl = "http://172.16.16.215:8000/generate-image";
            Map<String, String> aiRequest = Map.of("prompt", prompt);
            logger.info("===== ai 서버에서 받아오기 1 =====");
            byte[] image_url = restTemplate.postForObject(aiUrl, aiRequest, byte[].class);
            logger.info(image_url.toString());

            Map<String, byte[]> image = Map.of("image_url", image_url);
            return ResponseEntity.ok(image);

        } catch (Exception e) {
            logger.error(e.getMessage());
            // AI 서버 호출 실패
            return ResponseEntity.status(500).build();
        }

    }

        // 전체 조회 (jpql 사용해서 userid에 맞는 board 객체 불러오기)
//    // 사용자가 로그인 시, 사용자가 가지고 있는 게시글 전체 조회 메서드
//    @GetMapping("/album")
//    public ResponseEntity<List<BoardDTO>> getAllImages() {
//        logger.info("get : /getAllImages");
//
//        List<BoardDTO> ImageList = boardService.getAllImages();
//        return ResponseEntity.ok().body(ImageList);
//    }


    // 사용자가 게시글 등록
    @PostMapping("/post")
    public ResponseEntity<?> postBoard(@RequestBody BoardDTO boardDTO) {
        logger.info("===== /post 엔드포인트 호출됨 =====");
        logger.info("post : /post" + boardDTO.getCreatedAt());

        BoardDTO savedBoard = boardService.postBoard(boardDTO);
        return ResponseEntity.ok().body(savedBoard);
    }



}