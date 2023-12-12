package com.example.demo.models;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean is_completed;

    @ManyToOne
    @JoinColumn(name = "taskList_id", nullable = false)
    private TaskListModel taskList;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;
}