package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kanban_columns")
public class KanbanColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "kanbanColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteModel> notes;

    // @OneToMany(mappedBy = "kanbanColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<TaskListModel> taskLists;
}
