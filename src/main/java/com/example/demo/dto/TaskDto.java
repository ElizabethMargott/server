package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String content;
    private Boolean isCompleted;
    private Long taskListId;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
