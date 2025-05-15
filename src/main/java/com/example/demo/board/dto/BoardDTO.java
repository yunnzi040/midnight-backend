package com.example.demo.board.dto;


import java.time.LocalDateTime;
import java.util.Arrays;

public class BoardDTO {

    private Long boardId;
    private byte[] imageUrl;
    private LocalDateTime createdAt;
    private Long userId;
    private String emotion;
    private String content;

    public BoardDTO() {}

    public BoardDTO(Long boardId, byte[] imageUrl, LocalDateTime createdAt, Long userId, String emotion, String content) {
        this.boardId = boardId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.userId = userId;
        this.emotion = emotion;
        this.content = content;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "boardId=" + boardId +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", emotion='" + emotion + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
