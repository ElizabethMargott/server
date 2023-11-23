package com.example.demo.services;

import com.example.demo.dto.NoteDto;
import com.example.demo.models.NoteModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.INoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final INoteRepository noteRepository;

    public NoteService(INoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<NoteDto> getNotesByUserDto(UserModel user) {
        List<NoteModel> notes = noteRepository.findByUser(user);
        return notes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public NoteDto saveNote(NoteDto noteDto, UserModel currentUser) {
        NoteModel note = new NoteModel();
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription()); // Asegúrate de que este valor no sea null
        note.setContent(noteDto.getContent());
        note.setUser(currentUser);
        NoteModel savedNote = noteRepository.save(note);
        return convertToDto(savedNote);
    }

    public NoteDto getNoteById(Long id) {
        NoteModel note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        return convertToDto(note);
    }

    public NoteDto updateNoteById(Long id, NoteDto noteDto) {
        NoteModel note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setModificationDate(LocalDateTime.now());
        return convertToDto(noteRepository.save(note));
    }

    public void deleteNoteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note not found");
        }
        noteRepository.deleteById(id);
    }
    
    private NoteDto convertToDto(NoteModel note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDescription(note.getDescription());
        dto.setContent(note.getContent());
        dto.setUserId(note.getUser().getId());
        dto.setCreationDate(note.getCreationDate());
        dto.setModificationDate(note.getModificationDate());
        return dto;
    }

}
