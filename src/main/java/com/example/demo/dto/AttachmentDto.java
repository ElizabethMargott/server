package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AttachmentDto {
    private Long id;
    private Long noteId;
    private String fileType;
    private String filePath;
    private LocalDateTime createdAt;
}