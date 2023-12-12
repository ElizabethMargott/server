package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.models.KanbanColumn;

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
    private Long kanbanColumn;
}