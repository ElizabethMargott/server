package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.AttachmentModel;

@Repository
public interface IAttachmentRepository extends JpaRepository<AttachmentModel, Long> {
    List<AttachmentModel> findByNoteId(Long noteId);
}
