package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskListDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}