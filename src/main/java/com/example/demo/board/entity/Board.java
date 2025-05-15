package com.example.demo.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Lob
    @Column(name = "image_url", columnDefinition = "MEDIUMBLOB")
    private byte[] imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "content")
    private String content;

    public Board() {}

    public Board(Long id, byte[] imageUrl, LocalDateTime createdAt, Long userId, String emotion, String content) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.userId = userId;
        this.emotion = emotion;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", emotion='" + emotion + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
