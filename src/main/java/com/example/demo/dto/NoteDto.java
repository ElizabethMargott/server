package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long userId;
}