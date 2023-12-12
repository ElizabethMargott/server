package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.models.KanbanColumn;

@Repository
public interface KanbanColumnRepository extends JpaRepository<KanbanColumn, Long> {}