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
@Table(name = "notes")
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 255)
    private String title, description, content;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<AttachmentModel> attachments;

    @ManyToOne
    @JoinColumn(name = "kanbanColumn_id", referencedColumnName = "id")
    private KanbanColumn kanbanColumn;
}