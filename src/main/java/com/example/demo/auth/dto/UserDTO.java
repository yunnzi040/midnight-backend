package com.example.demo.auth.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createTime;

    public UserDTO() {}

    public UserDTO(Long id, String username, String password, String email, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
