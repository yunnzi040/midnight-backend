package com.example.demo.board.service;

import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public BoardDTO saveImage (Long userId, byte[] imageUrl) {

        // userId 검사
        if (userId == null) {
            throw new IllegalArgumentException("userId should not be null");
        }

        Board board = new Board();
        board.setImageUrl(imageUrl);
        board.setUserId(userId);
        board.setCreatedAt(LocalDateTime.now());

        Board savedImage = boardRepository.save(board);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardId(savedImage.getId());
        boardDTO.setImageUrl(savedImage.getImageUrl());
        boardDTO.setCreatedAt(savedImage.getCreatedAt());
        boardDTO.setUserId(savedImage.getUserId());
        boardDTO.setEmotion(savedImage.getEmotion());

        return boardDTO;
    }

//    public List<BoardDTO> getAllImages () {
//        List<Board> imageList = boardRepository.findAllById();
//
//        if (imageList.isEmpty()) {
//            throw new IllegalArgumentException("imageList should not be empty");
//        }
//
//        List<BoardDTO> boardDTOS = new ArrayList<>();
//        for (Board board : imageList) {
//            BoardDTO item = new BoardDTO(board.getId(), board.getImageUrl(), board.getCreatedAt(), board.getUserId(), board.getEmotion(), null);
//            boardDTOS.add(item);
//        }
//
//        return boardDTOS;
//    }


    // 게시글 등록
    public BoardDTO postBoard(BoardDTO boardDTO) {

        Board board = new Board();
        board.setImageUrl(boardDTO.getImageUrl());
        board.setCreatedAt(LocalDateTime.now());
        board.setUserId(boardDTO.getUserId());
        board.setEmotion(boardDTO.getEmotion());
        board.setContent(boardDTO.getContent());

        boardRepository.save(board);

        return new BoardDTO(board.getId(), board.getImageUrl(), board.getCreatedAt(), board.getUserId(), board.getEmotion(), board.getContent());
    }
}