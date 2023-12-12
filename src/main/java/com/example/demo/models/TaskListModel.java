package com.example.demo.models;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_lists")
public class TaskListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false, length = 255)
    private String title;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private List<TaskModel> tasks;

    // @ManyToOne
    // @JoinColumn(name = "kanban_column_id")
    // private KanbanColumn kanbanColumn;
}