package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.models.UserModel;
import com.example.demo.user.Role;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String avatarFilename;
    private Role role;
    private LocalDateTime registrationDate;
    private LocalDateTime modificationDate;
    public UserModel orElseThrow(Object object) {
        return null;
    }
}
